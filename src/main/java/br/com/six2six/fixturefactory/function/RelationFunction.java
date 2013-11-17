package br.com.six2six.fixturefactory.function;

import br.com.six2six.fixturefactory.processor.Processor;


public interface RelationFunction extends Function {

	<T> T generateValue(Processor processor);
	
	<T> T generateValue(Object owner);
	
	<T> T generateValue(Object owner, Processor processor);
	
}
