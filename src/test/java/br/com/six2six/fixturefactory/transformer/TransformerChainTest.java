package br.com.six2six.fixturefactory.transformer;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import br.com.six2six.fixturefactory.transformer.Transformer;
import br.com.six2six.fixturefactory.transformer.TransformerChain;

public class TransformerChainTest {

    @Test
    public void shouldUseFirstTransformer() {
        TransformerChain transformerChain = new TransformerChain(new FirstTransformer());
        assertEquals("A1", transformerChain.transform("A", String.class));
    }
    
    @Test
    public void shouldUseSecondTransformer() {
        TransformerChain transformerChain = new TransformerChain(new FirstTransformer());
        transformerChain.add(new SecondTransformer());
        assertEquals("B2", transformerChain.transform("B", String.class));        
    }
    
    @Test
    public void shouldUseBothTransformersInOrderOfInclusion() {
        TransformerChain transformerChain = new TransformerChain(new FirstTransformer());
        transformerChain.add(new SecondTransformer());
        assertEquals("AB12", transformerChain.transform("AB", String.class));        
    }
    
    @Test
    public void shouldNotUseAnyOfTheTransformers() {
        TransformerChain transformerChain = new TransformerChain(new FirstTransformer());
        transformerChain.add(new SecondTransformer());
        assertEquals("C", transformerChain.transform("C", String.class));        
    }
}

class FirstTransformer implements Transformer {

    @SuppressWarnings("unchecked")
    public <T> T transform(Object value, Class<T> type) {
        return (T) value.toString().concat("1");
    }

    public boolean accepts(Object value, Class<?> type) {
        return ((String) value).contains("A") && String.class == type;
    }
}

class SecondTransformer implements Transformer {

    @SuppressWarnings("unchecked")
    public <T> T transform(Object value, Class<T> type) {
        return (T) value.toString().concat("2");
    }

    public boolean accepts(Object value, Class<?> type) {
        return ((String) value).contains("B") && String.class == type;
    }
}
