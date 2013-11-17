package br.com.six2six.fixturefactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.six2six.fixturefactory.context.Processor;
import br.com.six2six.fixturefactory.transformer.PropertyPlaceholderTransformer;
import br.com.six2six.fixturefactory.transformer.TransformerChain;
import br.com.six2six.fixturefactory.util.ReflectionUtils;

public class ObjectFactoryProcessor extends ObjectFactory {

	private final Processor processor;
	
	public ObjectFactoryProcessor(TemplateHolder templateHolder, Object owner, Processor processor) {
		super(templateHolder, owner);
		this.processor = processor;
	}
	
	public ObjectFactoryProcessor(TemplateHolder templateHolder, Processor processor) {
		super(templateHolder);
		this.processor = processor;
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
				constructorArguments.put(property.getName(), generateConstructorParamValue(property));
			} else {
				deferredProperties.add(property);
			}
		}
		
		Object result = ReflectionUtils.newInstance(clazz, processConstructorArguments(parameterNames, constructorArguments));
		
		for (Property property : deferredProperties) {
			ReflectionUtils.invokeRecursiveSetter(result, property.getName(), processPropertyValue(result, property));
		}
		
		processor.execute(result);
		
		return result;
	}
	
	private Object generateConstructorParamValue(Property property) {
		if(property.hasRelationFunction()) {
			return property.getValue(processor);
		} else {
			return property.getValue();
		}
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
		return new ObjectFactoryProcessor(new TemplateHolder(fieldType), processor).createObject(rule);
		
	}
	
	@Override
	protected Object processPropertyValue(Object object, Property property) {
		Class<?> fieldType = ReflectionUtils.invokeRecursiveType(object.getClass(), property.getName());
		Object value = property.hasRelationFunction() || ReflectionUtils.isInnerClass(fieldType) ?
				property.getValue(object, processor) : property.getValue();
		
		TransformerChain transformerBaseChain = buildTransformerChain(new PropertyPlaceholderTransformer(object));
		return transformerBaseChain.transform(value, fieldType);
	}
}
