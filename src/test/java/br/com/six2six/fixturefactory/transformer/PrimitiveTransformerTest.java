package br.com.six2six.fixturefactory.transformer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class PrimitiveTransformerTest {
    
    @Test
    public void shouldNotAcceptNonStringValue() {
        assertFalse(new PrimitiveTransformer().accepts(new Object(), long.class));
    }
    
    @Test
    public void shouldNotAcceptNonPrimitiveTarget() {
        assertFalse(new PrimitiveTransformer().accepts("1", Object.class));
    }
    
    @Test
    public void shouldAcceptStringToNumericType() {
        assertTrue(new PrimitiveTransformer().accepts("1", int.class));
    }
    
    @Test
    public void shouldAcceptStringToBooleanType() {
        assertTrue(new PrimitiveTransformer().accepts("1", boolean.class));
    }
    
    @Test
    public void shouldTransformToNumberTarget() {
        long result = new PrimitiveTransformer().transform("1", long.class);
        assertEquals(1L, result);
    }
    
    @Test
    public void shouldTransformToBooleanTarget() {
        boolean result = new PrimitiveTransformer().transform("true", boolean.class);
        assertTrue(result);
    }
}
