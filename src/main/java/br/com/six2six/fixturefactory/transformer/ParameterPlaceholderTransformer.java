package br.com.six2six.fixturefactory.transformer;

import java.util.Map;

public class ParameterPlaceholderTransformer extends PlaceholderTransformer {
    
    private final Map<String, Object> parameters;
    
    public ParameterPlaceholderTransformer(final Map<String, Object> parameters) {
        this.parameters = parameters;
    }

    @Override
    protected String getValue(String parameterName) {
        return parameters.get(parameterName).toString();
    }
}