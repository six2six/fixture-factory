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
		
		Object result = instantiate(templateHolder.getClazz(), getActualParameterValues(parameterNames, parameters));
		
		for (Property property : deferredProperties) {
			Class<?> fieldType = ReflectionUtils.invokeRecursiveType(result.getClass(), property.getName());
			Object value = property.hasRelationFunction() || ReflectionUtils.isInnerClass(fieldType) ?
					property.getValue(result) : property.getValue();
			
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

	private List<Object> getActualParameterValues(List<String> parameterNames, Map<String, Object> parameters) {
		List<Object> parameterValues = new ArrayList<Object>();
		
		if (owner != null && ReflectionUtils.isInnerClass(templateHolder.getClazz()))  {
			parameterValues.add(owner);	
		}
		
		for (String parameterName : parameterNames) {
			Object value = parameters.get(parameterName);

			if (value instanceof String) {
				String baseValue = (String) value;
				int start = baseValue.indexOf(PLACE_HOLDER_START);
				if (start >= 0) {
					String propertyReference = baseValue.substring(start + PLACE_HOLDER_START.length(), baseValue.indexOf(PLACE_HOLDER_END));
					value = baseValue.replace(PLACE_HOLDER_START + propertyReference + PLACE_HOLDER_END, parameters.get(propertyReference).toString());
				}
			}
			
			if (value instanceof Calendar) {
				Class<?> fieldType = ReflectionUtils.invokeRecursiveType(templateHolder.getClazz(), parameterName);
				value = new CalendarTransformer().transform(value, fieldType);
			}
			
			parameterValues.add(value);
		}
		return parameterValues;
	}
	
	private <T> List<String> resolveConstructorParameterNames(Class<T> target, Set<Property> properties) {
		MirrorList<Constructor<T>> constructors = new Mirror().on(target).reflectAll().constructors();

		List<String> args = Collections.emptyList();
		
		Paranamer paranamer = new AdaptiveParanamer();
		
		Collection<String> propertyNames = ReflectionUtils.map(properties, "name");
		
		for (Constructor<T> constructor : constructors) {
			String[] parameterNames = paranamer.lookupParameterNames(constructor, false);
			if (propertyNames.containsAll(Arrays.asList(parameterNames))) {
				if (args.size() < parameterNames.length) {
					args = Arrays.asList(parameterNames);
				}
			}
		}
		return args;
	}
	
	public <T> T instantiate(Class<T> target, List<	Object> parameters) {
		if (parameters.size() > 0) {
			return new Mirror().on(target).invoke().constructor().withArgs(parameters.toArray());			
		} else {
			return new Mirror().on(target).invoke().constructor().withoutArgs();
		}		
	}
}
