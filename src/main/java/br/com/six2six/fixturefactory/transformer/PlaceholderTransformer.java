package br.com.six2six.fixturefactory.transformer;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class PlaceholderTransformer implements Transformer {

    private static final Pattern PLACEHOLDER = Pattern.compile("(\\$\\{([^\\}]+)\\})");

    protected abstract String getValue(String name);

    public <T> T transform(Object value, Class<T> type) {
        Object result = value;
        
        Matcher matcher = PLACEHOLDER.matcher((String) result);
        while (matcher.find()) {
            result = ((String) result).replace(matcher.group(1), getValue(matcher.group(2)));
        }            

        return type.cast(result);
    }
    
    public boolean accepts(Object value, Class<?> type) {
        return value instanceof String && type == String.class;
    }
}
