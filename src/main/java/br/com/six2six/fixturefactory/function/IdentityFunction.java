package br.com.six2six.fixturefactory.function;

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
