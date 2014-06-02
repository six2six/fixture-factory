package br.com.six2six.fixturefactory.function.impl;

import br.com.six2six.fixturefactory.function.AtomicFunction;

public class IdentityFunction implements AtomicFunction {
    
    private Object value;
    
    public IdentityFunction(Object value) {
        this.value = value;
    }
    
    @Override
    @SuppressWarnings("unchecked")
	public <T> T generateValue() {
		return (T) this.value;
	}
}
