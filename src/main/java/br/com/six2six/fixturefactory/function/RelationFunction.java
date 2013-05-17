package br.com.six2six.fixturefactory.function;


public interface RelationFunction extends Function {

	<T> T generateValue(Object owner);
	
}
