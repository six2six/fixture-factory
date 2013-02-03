package br.com.fixturefactory;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import br.com.fixturefactory.util.CalendarTransformer;
import br.com.fixturefactory.util.ReflectionUtils;

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
		Object result;
		if (owner == null || !ReflectionUtils.isInnerClass(templateHolder.getClazz()))  {
			result = ReflectionUtils.newInstance(templateHolder.getClazz());	
		} else {
			result = ReflectionUtils.newInnerClassInstance(templateHolder.getClazz(), owner);	
		}
		
		
		for (Property property : rule.getProperties()) {
			Class<?> fieldType = ReflectionUtils.invokeRecursiveType(result, property.getName());
			
			Object value = property.hasRelationFunction() || ReflectionUtils.isInnerClass(fieldType) ?
					property.getValue(result) : property.getValue();
			
			if (Number.class.isAssignableFrom(fieldType) && value instanceof String) {
				value = ReflectionUtils.parseObjectFromString(value.toString(), fieldType);
			}
			
			if (value instanceof String) {
				String baseValue = (String) value;
				int start = baseValue.indexOf(PLACE_HOLDER_START);
				if (start >= 0) {
					String propertyReference = baseValue.substring(start + PLACE_HOLDER_START.length(), baseValue.indexOf(PLACE_HOLDER_END));
					value = baseValue.replace(PLACE_HOLDER_START + propertyReference + PLACE_HOLDER_END, ReflectionUtils.invokeRecursiveGetter(result, propertyReference).toString());
				}
			}
		
			if (value instanceof Calendar) {
				value = new CalendarTransformer().transform(value, fieldType);
			}
			
			ReflectionUtils.invokeRecursiveSetter(result, property.getName(), value);
		}
		
		return result;
	}
}
