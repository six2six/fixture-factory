package br.com.six2six.fixturefactory.function;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang.ClassUtils;
import org.apache.commons.lang.StringUtils;

import br.com.six2six.fixturefactory.processor.Processor;
import br.com.six2six.fixturefactory.util.ReflectionUtils;

public class AssociationFunctionImpl implements AssociationFunction {

	private String targetAttribute;
	private Class<?> clazz;
	private List<String> labels;
	private Integer quantity;

	public AssociationFunctionImpl(Integer quantity) {
		this.quantity = quantity;
	}
	
	public AssociationFunctionImpl(Class<?> clazz, String label) {
		this.clazz = clazz;
		this.labels = Arrays.asList(label);
	}
	
	public AssociationFunctionImpl(String targetAttribute) {
		this.targetAttribute = targetAttribute;
	}

	@Override
	public <T> T generateValue() {
		return new FixtureFunction(clazz, labels, quantity).generateValue();
	}
	
	@Override
	public <T> T generateValue(Processor processor) {
		return new FixtureFunction(clazz, labels, quantity).generateValue(processor);
	}
	
	@Override
	public <T> T generateValue(Object owner) {
		T target = new FixtureFunction(clazz, labels, quantity).generateValue(owner);
		
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
	public <T> T generateValue(Object owner, Processor processor) {
		T target = new FixtureFunction(clazz, labels, quantity).generateValue(owner, processor);
		
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
	public AssociationFunction of(Class<?> clazz, String label) {
		this.clazz = clazz;
		this.labels = Arrays.asList(label);
		return this;
	}
	
	@Override
	public AssociationFunction of(Class<?> clazz, String... labels) {
		this.clazz = clazz;
		this.labels = Arrays.asList(labels);
		return this;
	}
	
	@Override
	public Function of(Class<? extends Enum<?>> clazz) {
		return new EnumFunction(clazz, quantity);
	}
	
	@Override
	public Function targetAttribute(String targetAttribute) {
		this.targetAttribute = targetAttribute;
		return this;
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
