package br.com.six2six.fixturefactory.transformer;

public interface Transformer {

    <T> T transform(Object value, Class<T> type);

    boolean accepts(Object value, Class<?> type);
}
