package br.com.six2six.fixturefactory.transformer;

import java.util.Collection;
import java.util.HashSet;
import java.util.NavigableSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

public class SetTransformer implements Transformer {

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public <T> T transform(Object value, Class<T> type) {
    	if (SortedSet.class.isAssignableFrom(type) || NavigableSet.class.isAssignableFrom(type)) {
    		return type.cast(new TreeSet((Collection) value));
    	}
    	return type.cast(new HashSet((Collection) value));
    }

    public boolean accepts(Object value, Class<?> type) {
        return value instanceof Collection && Set.class.isAssignableFrom(type);
    }
}