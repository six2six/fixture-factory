package br.com.six2six.fixturefactory.transformer;

import java.util.Arrays;

import br.com.six2six.fixturefactory.util.ReflectionUtils;

public class WrapperTransformer implements Transformer {

    public <T> T transform(Object value, Class<T> type) {
        return ReflectionUtils.newInstance(type, Arrays.asList(value));
    }

    public boolean accepts(Object value, Class<?> type) {
        return value instanceof String && (Number.class.isAssignableFrom(type) || Boolean.class.isAssignableFrom(type));
    }
}