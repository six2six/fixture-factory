package br.com.six2six.fixturefactory.function.impl;

import br.com.six2six.fixturefactory.base.Sequence;
import br.com.six2six.fixturefactory.function.AtomicFunction;

public class SequenceFunction implements AtomicFunction {

	private Sequence<?> sequence;
	
	public SequenceFunction(Sequence<?> sequence) {
		this.sequence = sequence;
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T> T generateValue() {
		return (T) sequence.nextValue();
	}
}
