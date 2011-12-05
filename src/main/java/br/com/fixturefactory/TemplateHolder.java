package br.com.fixturefactory;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import br.com.fixturefactory.util.CalendarTransformer;
import br.com.fixturefactory.util.ReflectionUtils;

public class TemplateHolder {

	private static final String PLACE_HOLDER_START = "${";
	private static final String PLACE_HOLDER_END = "}";

	private Class<?> clazz;
	
	private Map<String, Rule> rules = new LinkedHashMap<String, Rule>();
	
	public TemplateHolder(Class<?> clazz) {
		this.clazz = clazz;
	}

	public TemplateHolder addTemplate(String label, Rule rule) {
		rules.put(label, rule);
		return this;
	}

	public Class<?> getClazz() {
		return clazz;
	}

	public Map<String, Rule> getRules() {
		return rules;
	}
	
	@SuppressWarnings("unchecked")
	public <T> T gimme(String label) {
		Rule rule = this.rules.get(label);
		 	
		if (rule == null) {
			throw new IllegalArgumentException("No such label: " + label);
		}

		return (T) this.createObject(rule);
	}

	@SuppressWarnings("unchecked")
	public <T> List<T> gimme(Integer quantity, String label) {
		Rule rule = this.rules.get(label);
		
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
		Object result = ReflectionUtils.newInstance(this.clazz);
		
		for (Property property : rule.getProperties()) {
			Object value = property.hasRelationFunction() ? property.getValue(result) : property.getValue();
			
			if (value instanceof String) {
				String baseValue = (String) value;
				int start = baseValue.indexOf(PLACE_HOLDER_START);
				if (start >= 0) {
					String propertyReference = baseValue.substring(start + PLACE_HOLDER_START.length(), baseValue.indexOf(PLACE_HOLDER_END));
					value = baseValue.replace(PLACE_HOLDER_START + propertyReference + PLACE_HOLDER_END, ReflectionUtils.invokeRecursiveGetter(result, propertyReference).toString());
				}
			}
		
			if (value instanceof Calendar) {
				Class<?> fieldType = ReflectionUtils.invokeRecursiveType(result, property.getName());
				value = new CalendarTransformer().transform(value, fieldType);
			}
			
			ReflectionUtils.invokeRecursiveSetter(result, property.getName(), value);
		}
		
		return result;
	}

}
