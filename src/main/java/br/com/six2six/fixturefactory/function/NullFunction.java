package br.com.six2six.fixturefactory.function;

public class NullFunction implements AtomicFunction {

	@Override
	public <T> T generateValue() {
		return null;
	}

}
