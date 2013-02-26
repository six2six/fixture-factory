package br.com.fixturefactory.function;

import java.lang.reflect.Field;
import java.util.Collection;

import org.apache.commons.lang.ClassUtils;
import org.apache.commons.lang.StringUtils;

import br.com.fixturefactory.util.Chainable;
import br.com.fixturefactory.util.ReflectionUtils;

public class AssociationFunction implements AtomicFunction, RelationFunction, Chainable {

	private FixtureFunction fixtureFunction;
	
	private String targetAttribute;
	
	public AssociationFunction(FixtureFunction fixtureFunction) {
		this.fixtureFunction = fixtureFunction;
	}
	
	public AssociationFunction(FixtureFunction fixtureFunction, String targetAttribute) {
		this(fixtureFunction);
		this.targetAttribute = targetAttribute;
	}

	@Override
	public <T> T generateValue() {
		return fixtureFunction.generateValue();
	}
	
	@Override
	public <T> T generateValue(Object owner) {
		T target = fixtureFunction.generateValue(owner);
		
		if (target instanceof Collection<?>) {
			for (Object item : (Collection<?>) target) {
				this.setField(item, owner);			
			}
		} else {
			this.setField(target, owner);
		}
		
		return target;
	}
	
	private void setField(Object target, Object value) {
	    String fieldName = targetAttribute;

	    if (StringUtils.isBlank(targetAttribute)) {
	        Field field = this.getAssignableField(target.getClass(), value.getClass());
	        if (field != null) {
	            fieldName = field.getName();
	        }
	    }

	    if (!StringUtils.isBlank(fieldName)) {
	        ReflectionUtils.invokeRecursiveSetter(target, fieldName, value);
	    }
	}
	
	private Field getAssignableField(Class<?> clazz, Class<?> fieldType) {
		Field searchdField = null;
		
		for (Field field : clazz.getDeclaredFields()) {
			if (ClassUtils.isAssignable(field.getType(), fieldType) && !field.isSynthetic()) {
				searchdField = field;
				break;
			}
		}
		
		return searchdField;
	}

	@Override
	public Function of(Class<?> clazz, String label) {
		this.configureFixtureFunction(clazz, label);
		return this;
	}

	@Override
	public Function of(Class<?> clazz, String label, String targetAttribute) {
		this.configureFixtureFunction(clazz, label);
		this.targetAttribute = targetAttribute;
		return this;
	}
	
	private void configureFixtureFunction(Class<?> clazz, String label) {
		ReflectionUtils.invokeRecursiveSetter(fixtureFunction, "clazz", clazz);
		ReflectionUtils.invokeRecursiveSetter(fixtureFunction, "label", label);
	}
	
}
