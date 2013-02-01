package br.com.fixturefactory.function;

import java.util.Collection;
import java.util.HashSet;

public class SetCastFunction implements RelationFunction {

	private RelationFunction function;
	
	public SetCastFunction(RelationFunction function) {
		this.function = function;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public <T> T generateValue(Object owner) {
		Object generateValue = function.generateValue(owner);
		
		if(!(generateValue instanceof Collection<?>)) throw new IllegalArgumentException("only supports Collection values ~>" + generateValue.getClass().getName());
		
		return (T) new HashSet(((Collection<?>)generateValue));
	}

}
