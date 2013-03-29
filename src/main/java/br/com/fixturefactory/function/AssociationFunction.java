package br.com.fixturefactory.function;

import java.lang.reflect.Field;
import java.util.Collection;

import org.apache.commons.lang.ClassUtils;
import org.apache.commons.lang.StringUtils;

import br.com.fixturefactory.util.Chainable;
import br.com.fixturefactory.util.ReflectionUtils;

public class AssociationFunction implements AtomicFunction, RelationFunction, Chainable {

	private String targetAttribute;
	private Class<?> clazz;
	private String label;
	private Integer quantity;

	public AssociationFunction(Integer quantity) {
		this.quantity = quantity;
	}
	
	public AssociationFunction(Class<?> clazz, String label) {
		this.clazz = clazz;
		this.label = label;
	}
	
	public AssociationFunction(Class<?> clazz, String label, String targetAttribute) {
		this.clazz = clazz;
		this.label = label;
		this.targetAttribute = targetAttribute;
	}
	
	public AssociationFunction(String targetAttribute) {
		this.targetAttribute = targetAttribute;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T generateValue() {
		if (clazz.isEnum()) {
			return new EnumFixtureFunction((Class<? extends Enum<?>>) clazz, quantity).generateValue();
		} else {
			return getFixtureFunction().generateValue();
		}
	}
	
	@Override
	public <T> T generateValue(Object owner) {
		T target = generateFunctionValue(owner);
		
		if (target instanceof Collection<?>) {
			for (Object item : (Collection<?>) target) {
				this.setField(item, owner);			
			}
		} else {
			this.setField(target, owner);
		}
		
		return target;
	}
	
	@Override
	public Function of(Class<?> clazz, String label) {
		this.clazz = clazz;
		this.label = label;
		return this;
	}
	
	@Override
	public Function of(Class<?> clazz, String label, String targetAttribute) {
		this.clazz = clazz;
		this.label = label;
		this.targetAttribute = targetAttribute;
		return this;
	}
	
	@Override
	public Function of(Class<? extends Enum<?>> clazz) {
		this.clazz = clazz;
		
		return this;
	}
	
	private FixtureFunction getFixtureFunction() {
		if (quantity != null) {
			return new FixtureFunction(clazz, label, quantity);
		} else {
			return new FixtureFunction(clazz, label);
		}
	}
	
	@SuppressWarnings("unchecked")
	private <T> T generateFunctionValue(Object owner) {
		if (clazz.isEnum()) {
			return new EnumFixtureFunction((Class<? extends Enum<?>>) clazz, quantity).generateValue();
		} else {
			return getFixtureFunction().generateValue(owner);
		}
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
	
}
