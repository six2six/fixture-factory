package br.com.six2six.fixturefactory.transformer;

import java.util.Arrays;
import java.util.Collection;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import br.com.six2six.fixturefactory.util.ReflectionUtils;

public class SetTransformer implements Transformer {

    public <T> T transform(Object value, Class<T> type) {
        if (type.isAssignableFrom(value.getClass())) {
            return type.cast(value);
        } else {
            return transformToSet(value, type);
        }
    }

    public boolean accepts(Object value, Class<?> type) {
        return value instanceof Collection && Set.class.isAssignableFrom(type);
    }

    private <T> T transformToSet(Object value, Class<T> type) {
        if (type.isInterface()) {
            return transformToInterfaceTarget(value, type);
        } else {
            return transformToConcreteTarget(value, type);
        }
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    private <T> T transformToInterfaceTarget(Object value, Class<T> type) {
        if (SortedSet.class.isAssignableFrom(type)) {
            return type.cast(new TreeSet((Collection) value));
        } else {
            return type.cast(new HashSet((Collection) value));
        }
    }

    @SuppressWarnings({ "rawtypes", "unchecked" , ""})
    private <T> T transformToConcreteTarget(Object value, Class<T> type) {
        if (EnumSet.class.isAssignableFrom(type)) {
            return (T) type.cast(EnumSet.copyOf((Collection) value));
        } else {
            return ReflectionUtils.newInstance(type, Arrays.asList((Collection) value));
        }
    }
}
