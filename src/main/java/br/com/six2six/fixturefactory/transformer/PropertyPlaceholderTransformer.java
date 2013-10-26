package br.com.six2six.fixturefactory.transformer;

import br.com.six2six.fixturefactory.util.ReflectionUtils;

public class PropertyPlaceholderTransformer extends PlaceholderTransformer {
    
    private final Object result;
    
    public PropertyPlaceholderTransformer(final Object result) {
        this.result = result;
    }

    @Override
    protected String getValue(String propertyName) {
        return ReflectionUtils.invokeRecursiveGetter(result, propertyName).toString();
    }
}