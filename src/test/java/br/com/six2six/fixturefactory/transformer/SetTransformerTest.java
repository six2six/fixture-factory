package br.com.six2six.fixturefactory.transformer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.junit.Test;

public class SetTransformerTest {
    
    @Test
    public void shouldNotAcceptNonCollectionValue() {
        assertFalse(new SetTransformer().accepts(new HashMap<String, Object>(), Set.class));
    }
    
    @Test
    public void shoudNotAcceptNonSetTarget() {
        assertFalse(new SetTransformer().accepts(new ArrayList<String>(), LinkedList.class));
    }
    
    @Test
    public void shouldAcceptCollectionToSet() {
        assertTrue(new SetTransformer().accepts(Arrays.asList("A", "B"), Set.class));        
    }

    @Test
    @SuppressWarnings("unchecked")
    public void shoudTransformToSet() {
        List<String> value = Arrays.asList("A", "B");
        Set<String> result = new SetTransformer().transform(value, Set.class);
        
        assertTrue(result.containsAll(value));
        assertEquals(2, result.size());
    }
}
