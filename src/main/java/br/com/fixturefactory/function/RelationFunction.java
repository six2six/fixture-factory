package br.com.fixturefactory.function;

public interface RelationFunction extends Function {

	<T> T generateValue(Object owner);
	
}
