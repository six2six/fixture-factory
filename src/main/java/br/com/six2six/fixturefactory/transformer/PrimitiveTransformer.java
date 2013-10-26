package br.com.six2six.fixturefactory.transformer;

import java.util.Arrays;

import org.apache.commons.lang.ClassUtils;

import br.com.six2six.fixturefactory.util.ReflectionUtils;

public class PrimitiveTransformer implements Transformer {

    @SuppressWarnings("unchecked")
    public <T> T transform(Object value, Class<T> type) {
        return (T) ReflectionUtils.newInstance(ClassUtils.primitiveToWrapper(type), Arrays.asList(value));
    }

    public boolean accepts(Object value, Class<?> type) {
        return value instanceof String && type.isPrimitive();
    }
}
