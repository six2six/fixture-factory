package br.com.six2six.fixturefactory.transformer;

public class TransformerChain {

    private Transformer transformer;
    private TransformerChain next;

    public TransformerChain(Transformer transformer) {
        this.transformer = transformer;
    }

    public Object transform(Object value, Class<?> type) {
        if (transformer.accepts(value, type)) {
            value = transformer.transform(value, type);
        } 
        
        if (next != null) {
            value = next.transform(value, type);
        }

        return value;
    }

    public void add(Transformer transformer) {
        if (this.next == null) {
            this.next = new TransformerChain(transformer);
        } else {
            this.next.add(transformer);
        }
    }
}
