package br.com.six2six.fixturefactory.function;


public interface AtomicFunction extends Function {

	<T> T generateValue();
	
}
