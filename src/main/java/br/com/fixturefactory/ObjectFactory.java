package br.com.fixturefactory;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.vidageek.mirror.dsl.Mirror;
import net.vidageek.mirror.list.dsl.MirrorList;
import br.com.fixturefactory.util.CalendarTransformer;
import br.com.fixturefactory.util.ReflectionUtils;

import com.thoughtworks.paranamer.AdaptiveParanamer;
import com.thoughtworks.paranamer.Paranamer;

public class ObjectFactory {

	private static final String PLACE_HOLDER_START = "${";
	private static final String PLACE_HOLDER_END = "}";
	
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
		Map<String, Object> parameters = new HashMap<String, Object>();
		List<Property> deferredProperties = new ArrayList<Property>();
		
		List<String> parameterNames = resolveConstructorParameterNames(templateHolder.getClazz(), rule.getProperties());
		
		for (Property property : rule.getProperties()) {
			if (parameterNames.contains(property.getName())) {
				parameters.put(property.getName(), property.getValue());
			} else {
				deferredProperties.add(property);
			}
		}
		
		Object result = ReflectionUtils.newInstance(templateHolder.getClazz(), new ConstructorParameterProcessor(parameters).apply(parameterNames));
		
		for (Property property : deferredProperties) {
			ReflectionUtils.invokeRecursiveSetter(result, property.getName(), new PropertyProcessor(result).apply(property));
		}
		
		return result;
	}

	private <T> List<String> resolveConstructorParameterNames(Class<T> target, Set<Property> properties) {
		MirrorList<Constructor<T>> constructors = new Mirror().on(target).reflectAll().constructors();

		List<String> args = Collections.emptyList();
		
		Paranamer paranamer = new AdaptiveParanamer();
		
		Collection<String> propertyNames = ReflectionUtils.map(properties, "name");
		
		for (Constructor<T> constructor : constructors) {
			List<String> parameterNames = Arrays.asList(paranamer.lookupParameterNames(constructor, false));
			if (args.size() < parameterNames.size() && propertyNames.containsAll(parameterNames)) {
				args = parameterNames;
			}
		}
		return args;
	}
	
	protected abstract class ValueProcessor {
		protected abstract String getPropertyReferenceValue(String propertyReference);
		
		protected Object process(Object baseValue, Class<?> fieldType) {
			Object value = baseValue;
			if (baseValue instanceof String) {
				String stringValue = (String) baseValue;
				int start = stringValue.indexOf(PLACE_HOLDER_START);
				if (start >= 0) {
					String propertyReference = stringValue.substring(start + PLACE_HOLDER_START.length(), stringValue.indexOf(PLACE_HOLDER_END));
					String referenceValue = getPropertyReferenceValue(propertyReference);
					value = stringValue.replace(PLACE_HOLDER_START + propertyReference + PLACE_HOLDER_END, referenceValue);
				}
			}
			if (value instanceof Calendar) {
				value = new CalendarTransformer().transform(value, fieldType);
			}
			
			return value;
		}
	}
	
	private class ConstructorParameterProcessor extends ValueProcessor {
		private final Map<String, Object> parameters;
		
		private ConstructorParameterProcessor(final Map<String, Object> parameters) {
			this.parameters = parameters;
		}
		
		private List<Object> apply(List<String> parameterNames) {
			List<Object> parameterValues = new ArrayList<Object>();
			
			if (owner != null && ReflectionUtils.isInnerClass(templateHolder.getClazz()))  {
				parameterValues.add(owner);	
			}
			
			for (String parameterName : parameterNames) {
				Class<?> fieldType = ReflectionUtils.invokeRecursiveType(templateHolder.getClazz(), parameterName);
				Object value = parameters.get(parameterName);

				parameterValues.add(process(value, fieldType));
			}
			return parameterValues;
		}

		@Override
		protected String getPropertyReferenceValue(String propertyReference) {
			return parameters.get(propertyReference).toString();
		}
	}
	
	private class PropertyProcessor extends ValueProcessor {
		private final Object result;
		
		private PropertyProcessor(final Object result) {
			this.result = result;
		}
		
		private Object apply(Property property) {
			Class<?> fieldType = ReflectionUtils.invokeRecursiveType(result.getClass(), property.getName());
			Object value = property.hasRelationFunction() || ReflectionUtils.isInnerClass(fieldType) ?
					property.getValue(result) : property.getValue();
			
			return process(value, fieldType);
		}
		
		@Override
		protected String getPropertyReferenceValue(String propertyReference) {
			return ReflectionUtils.invokeRecursiveGetter(result, propertyReference).toString();
		}
	}
}
