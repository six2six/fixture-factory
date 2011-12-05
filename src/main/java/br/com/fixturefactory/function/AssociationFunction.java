package br.com.fixturefactory.function;

import java.util.Collection;

import br.com.fixturefactory.util.ReflectionUtils;

public class AssociationFunction implements RelationFunction {

	private FixtureFunction fixtureFunction;
	
	private String targetAttribute;
	
	public AssociationFunction(FixtureFunction fixtureFunction, String targetAttribute) {
		this.fixtureFunction = fixtureFunction;
		this.targetAttribute = targetAttribute;
	}

	@Override
	public <T> T generateValue(Object owner) {
		Object target = fixtureFunction.generateValue();
		
		if (target instanceof Collection<?>) {
			for (Object item : (Collection<?>) target) {
				ReflectionUtils.invokeRecursiveSetter(item, targetAttribute, owner);
			}
		} else {
			ReflectionUtils.invokeRecursiveSetter(target, targetAttribute, owner);	
		}
		
		return (T) target;
	}
	

	
}
