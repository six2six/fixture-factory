package br.com.six2six.fixturefactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import br.com.six2six.fixturefactory.util.ReflectionUtils;

public class ObjectFactory {
	
	private static final String NO_SUCH_LABEL_MESSAGE = "%s-> No such label: %s";
	
	private TemplateHolder templateHolder;
	
	private Object owner;
	
	public ObjectFactory(TemplateHolder templateHolder) {
		this.templateHolder = templateHolder;
	}
	
	public ObjectFactory(TemplateHolder templateHolder, Object owner) {
		this.templateHolder = templateHolder;
		this.owner = owner;
	}
	
	@SuppressWarnings("unchecked")
	public <T> T gimme(String label) {
		Rule rule = templateHolder.getRules().get(label);
		 	
		if (rule == null) {
			throw new IllegalArgumentException(String.format(NO_SUCH_LABEL_MESSAGE, templateHolder.getClazz().getName(), label));
		}

		return (T) this.createObject(rule);
	}

	@SuppressWarnings("unchecked")
	public <T> List<T> gimme(Integer quantity, String label) {
		Rule rule = templateHolder.getRules().get(label);
		
		if (rule == null) {
			throw new IllegalArgumentException(String.format(NO_SUCH_LABEL_MESSAGE, templateHolder.getClazz().getName(), label));
		}

		List<T> results = new ArrayList<T>(quantity);
		for (int i = 0; i < quantity; i++) {
			results.add((T) this.createObject(rule));
		}	
		
		return results;
	}

	private Object createObject(Rule rule) {
		Map<String, Object> constructorArguments = new HashMap<String, Object>();
		List<Property> deferredProperties = new ArrayList<Property>();
		Class<?> clazz = templateHolder.getClazz();
		
		List<String> parameterNames = !ReflectionUtils.hasDefaultConstructor(clazz) ? 
										lookupConstructorParameterNames(clazz, rule.getProperties()) : new ArrayList<String>();
		
		for (Property property : rule.getProperties()) {
			if (parameterNames.contains(property.getRootAttribute())) {
				constructorArguments.put(property.getName(), property.getValue());
			} else {
				deferredProperties.add(property);
			}
		}
		
		Object result = ReflectionUtils.newInstance(clazz, processConstructorArguments(parameterNames, constructorArguments));
		
		for (Property property : deferredProperties) {
			ReflectionUtils.invokeRecursiveSetter(result, property.getName(), processPropertyValue(result, property));
		}
		
		return result;
	}
	
	private List<Object> processConstructorArguments(List<String> parameterNames, Map<String, Object> arguments) {
		List<Object> values = new ArrayList<Object>();
		
		if (owner != null && ReflectionUtils.isInnerClass(templateHolder.getClazz()))  {
			values.add(owner);	
		}
		
		ConstructorArgumentProcessor valueProcessor = new ConstructorArgumentProcessor(arguments);
		for (String parameterName : parameterNames) {
			Class<?> fieldType = ReflectionUtils.invokeRecursiveType(templateHolder.getClazz(), parameterName);
			Object result = arguments.get(parameterName);
			if (result == null) {
				result = processChainedProperty(parameterName, fieldType, arguments);	
			}
			values.add(valueProcessor.process(result, fieldType));
		}
		return values;
	}

	private Object processChainedProperty(String parameterName, Class<?> fieldType, Map<String, Object> arguments) {
		Rule rule = new Rule();
		for (final String argument : arguments.keySet()) {
			int index = argument.indexOf(".");
			if (index > 0 && argument.substring(0, index).equals(parameterName)) {
				rule.add(argument.substring(index+1), arguments.get(argument));
			}
		}
		return new ObjectFactory(new TemplateHolder(fieldType)).createObject(rule);
	}

	private Object processPropertyValue(Object object, Property property) {
		Class<?> fieldType = ReflectionUtils.invokeRecursiveType(object.getClass(), property.getName());
		Object value = property.hasRelationFunction() || ReflectionUtils.isInnerClass(fieldType) ?
				property.getValue(object) : property.getValue();
		
		return new PropertyProcessor(object).process(value, fieldType);
	}
	
	private <T> List<String> lookupConstructorParameterNames(Class<T> target, Set<Property> properties) {
		Collection<String> propertyNames = ReflectionUtils.map(properties, "rootAttribute");
		return ReflectionUtils.filterConstructorParameterNames(target, propertyNames);
	}
}
