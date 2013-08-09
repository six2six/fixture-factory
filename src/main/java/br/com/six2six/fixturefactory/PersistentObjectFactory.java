package br.com.six2six.fixturefactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;

import br.com.six2six.fixturefactory.util.ReflectionUtils;

public class PersistentObjectFactory extends ObjectFactory {

	private final Session session;
	
	public PersistentObjectFactory(TemplateHolder templateHolder, Object owner, Session session) {
		super(templateHolder, owner);
		this.session = session;
	}
	
	public PersistentObjectFactory(TemplateHolder templateHolder, Session session) {
		super(templateHolder);
		this.session = session;
	}

	@Override
	protected Object createObject(Rule rule) {
		Map<String, Object> constructorArguments = new HashMap<String, Object>();
		List<Property> deferredProperties = new ArrayList<Property>();
		Class<?> clazz = templateHolder.getClazz();
		
		List<String> parameterNames = !ReflectionUtils.hasDefaultConstructor(clazz) ? 
										lookupConstructorParameterNames(clazz, rule.getProperties()) : new ArrayList<String>();
		
		for (Property property : rule.getProperties()) {
			if (parameterNames.contains(property.getRootAttribute())) {
				if(property.hasRelationFunction()) {
					constructorArguments.put(property.getName(), property.getValue(session));
				} else {
					constructorArguments.put(property.getName(), property.getValue());
				}
			} else {
				deferredProperties.add(property);
			}
		}
		
		Object result = ReflectionUtils.newInstance(clazz, processConstructorArguments(parameterNames, constructorArguments));
		
		for (Property property : deferredProperties) {
			ReflectionUtils.invokeRecursiveSetter(result, property.getName(), processPropertyValue(result, property));
		}
		
		session.save(result);
		
		return result;
	}
	
	@Override
	protected Object processChainedProperty(String parameterName, Class<?> fieldType, Map<String, Object> arguments) {
		Rule rule = new Rule();
		for (final String argument : arguments.keySet()) {
			int index = argument.indexOf(".");
			if (index > 0 && argument.substring(0, index).equals(parameterName)) {
				rule.add(argument.substring(index+1), arguments.get(argument));
			}
		}
		return new PersistentObjectFactory(new TemplateHolder(fieldType), session).createObject(rule);
		
	}
	
	@Override
	protected Object processPropertyValue(Object object, Property property) {
		Class<?> fieldType = ReflectionUtils.invokeRecursiveType(object.getClass(), property.getName());
		Object value = property.hasRelationFunction() || ReflectionUtils.isInnerClass(fieldType) ?
				property.getValue(object, session) : property.getValue();
		
		Object propertyValue = new PropertyProcessor(object).process(value, fieldType);
		
		return propertyValue;
	}
	
}
