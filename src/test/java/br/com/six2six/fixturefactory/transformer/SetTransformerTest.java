package br.com.six2six.fixturefactory.transformer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.NavigableSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

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
    public void shouldTrasformAnyInstanceOfSet() {
       assertEquals(new SetTransformer().transform(new HashSet<String>(Arrays.asList("A", "B")), HashSet.class).size(), 2);
       assertEquals(new SetTransformer().transform(new HashSet<String>(Arrays.asList("A", "B")), Set.class).size(), 2);
       assertEquals(new SetTransformer().transform(EnumSet.of(Letters.A, Letters.B), EnumSet.class).size(), 2);
       assertEquals(new SetTransformer().transform(new LinkedHashSet<String>(Arrays.asList("A", "B")), SortedSet.class).size(), 2);
       assertEquals(new SetTransformer().transform(Arrays.asList("A", "B"), Set.class).size(), 2);
       assertEquals(new SetTransformer().transform(new HashSet<Letters>(Arrays.asList(Letters.A, Letters.B)), EnumSet.class).size(), 2);
       assertEquals(new SetTransformer().transform(new TreeSet<String>(Arrays.asList("A", "B")), HashSet.class).size(), 2);
       assertEquals(new SetTransformer().transform(Arrays.asList("A", "B"), LinkedHashSet.class).size(), 2);
    }

    private static enum Letters { A, B }

    @Test
    @SuppressWarnings("unchecked")
    public void shoudTransformToNavigableSet() {
        List<String> value = Arrays.asList("A", "B");
        NavigableSet<String> resultNavigableSet = new SetTransformer().transform(Arrays.asList("A", "B"), NavigableSet.class);

        assertTrue(resultNavigableSet.containsAll(value));
        assertEquals(2, resultNavigableSet.size());
    }

    @Test
    @SuppressWarnings("unchecked")
    public void shoudTransformToSortedSet() {
        List<String> value = Arrays.asList("A", "B");
        SortedSet<String> resultSortedSet = new SetTransformer().transform(Arrays.asList("A", "B"), SortedSet.class);

        assertTrue(resultSortedSet.containsAll(value));
        assertEquals(2, resultSortedSet.size());
    }
}
