package br.com.fixturefactory.function;

public interface AtomicFunction extends Function {

	<T> T generateValue();

}
