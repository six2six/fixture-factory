package br.com.six2six.fixturefactory.transformer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class WrapperTransformerTest {

    @Test
    public void shouldTransformToNumberTarget() {
        Long result = new WrapperTransformer().transform("1", Long.class);
        assertEquals(Long.valueOf(1), result);
    }
    
    @Test
    public void shouldTransformToBooleanTarget() {
        Boolean result = new WrapperTransformer().transform("true", Boolean.class);
        assertEquals(Boolean.TRUE, result);
    }
    
    @Test
    public void shouldNotAcceptNonStringValue() {
        assertFalse(new WrapperTransformer().accepts(new Object(), Long.class));
    }
    
    @Test
    public void shouldNotAcceptNonWrapperTarget() {
        assertFalse(new WrapperTransformer().accepts("1", long.class));
    }
    
    @Test
    public void shouldAcceptStringToNumericType() {
        assertTrue(new WrapperTransformer().accepts("1", Integer.class));
    }
    
    @Test
    public void shouldAcceptStringToBooleanType() {
        assertTrue(new WrapperTransformer().accepts("1", Boolean.class));
    }
}
