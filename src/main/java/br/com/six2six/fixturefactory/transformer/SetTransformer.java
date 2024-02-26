package br.com.six2six.fixturefactory.transformer;

import java.util.*;

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

    @SuppressWarnings({ "rawtypes", "unchecked" })
    private <T> T transformToConcreteTarget(Object value, Class<T> type) {
        Collection collection = (Collection) value;
        if (EnumSet.class.isAssignableFrom(type)) {
            EnumSet enumSet = EnumSet.copyOf(collection);
            return type.cast(enumSet);
        } else {
            List<Collection> list = Arrays.asList(collection);
            return ReflectionUtils.newInstance(type, list);
        }
    }
}
