package br.com.fixturefactory;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.com.fixturefactory.util.CalendarTransformer;
import br.com.fixturefactory.util.ReflectionUtils;

public class ObjectFactory {

	private static final Pattern PLACEHOLDER = Pattern.compile(".*?(\\$\\{([^\\}]+)\\}).*");
	
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
			throw new IllegalArgumentException("No such label: " + label);
		}

		return (T) this.createObject(rule);
	}

	@SuppressWarnings("unchecked")
	public <T> List<T> gimme(Integer quantity, String label) {
		Rule rule = templateHolder.getRules().get(label);
		
		if (rule == null) {
			throw new IllegalArgumentException("No such label: " + label);
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
		
		List<String> parameterNames = lookupConstructorParameterNames(templateHolder.getClazz(), rule.getProperties());
		
		for (Property property : rule.getProperties()) {
			if (parameterNames.contains(property.getName())) {
				constructorArguments.put(property.getName(), property.getValue());
			} else {
				deferredProperties.add(property);
			}
		}
		
		Object result = ReflectionUtils.newInstance(templateHolder.getClazz(), processConstructorArguments(parameterNames, constructorArguments));
		
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
			values.add(valueProcessor.process(arguments.get(parameterName), fieldType));
		}
		return values;
	}

	private Object processPropertyValue(Object object, Property property) {
		Class<?> fieldType = ReflectionUtils.invokeRecursiveType(object.getClass(), property.getName());
		Object value = property.hasRelationFunction() || ReflectionUtils.isInnerClass(fieldType) ?
				property.getValue(object) : property.getValue();
		
		return new PropertyProcessor(object).process(value, fieldType);
	}
	
	private <T> List<String> lookupConstructorParameterNames(Class<T> target, Set<Property> properties) {
		Collection<String> propertyNames = ReflectionUtils.map(properties, "name");
		return ReflectionUtils.filterConstructorParameterNames(target, propertyNames);
	}
	
	protected abstract class ValueProcessor {
		protected abstract String getValue(String name);
		
		protected Object process(Object baseValue, Class<?> fieldType) {
			Object result = baseValue;
			if (baseValue instanceof String) {
				Matcher matcher = PLACEHOLDER.matcher((String) baseValue);
				if (matcher.matches()) {
					result = ((String) baseValue).replace(matcher.group(1), getValue(matcher.group(2)));				
				}
			}
			if (baseValue instanceof Calendar) {
				result = new CalendarTransformer().transform(baseValue, fieldType);
			}
			
			return result;
		}
	}
	
	private class ConstructorArgumentProcessor extends ValueProcessor {
		private final Map<String, Object> parameters;
		
		private ConstructorArgumentProcessor(final Map<String, Object> parameters) {
			this.parameters = parameters;
		}

		@Override
		protected String getValue(String parameterName) {
			return parameters.get(parameterName).toString();
		}
	}
	
	private class PropertyProcessor extends ValueProcessor {
		private final Object result;
		
		private PropertyProcessor(final Object result) {
			this.result = result;
		}

		@Override
		protected String getValue(String propertyName) {
			return ReflectionUtils.invokeRecursiveGetter(result, propertyName).toString();
		}
	}
}
