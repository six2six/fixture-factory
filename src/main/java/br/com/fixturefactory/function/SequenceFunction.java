package br.com.fixturefactory.function;

import br.com.fixturefactory.base.Sequence;

public class SequenceFunction implements Function {

	private Sequence<?> sequence;
	
	public SequenceFunction(Sequence<?> sequence) {
		super();
		this.sequence = sequence;
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T> T generateValue() {
		return (T) sequence.nextValue();
	}
}
