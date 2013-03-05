package br.com.fixturefactory;

import java.awt.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.com.fixturefactory.util.CalendarTransformer;
import br.com.fixturefactory.util.ReflectionUtils;

abstract class ValueProcessor {
    
    private static final Pattern PLACEHOLDER = Pattern.compile(".*?(\\$\\{([^\\}]+)\\}).*");
    
    protected abstract String getValue(String name);
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public Object process(Object baseValue, Class<?> fieldType) {
        Object result = baseValue;
        
        if (baseValue instanceof String) {
            Matcher matcher = PLACEHOLDER.matcher((String) baseValue);
            if (matcher.matches()) {
                result = ((String) baseValue).replace(matcher.group(1), getValue(matcher.group(2)));                
            } else if(Number.class.isAssignableFrom(fieldType)) {
            	result = ReflectionUtils.newInstance(fieldType, Arrays.asList(baseValue));
            }
        }
        if (baseValue instanceof Calendar) {
            result = new CalendarTransformer().transform(baseValue, fieldType);
        }
        if(baseValue instanceof Collection && !fieldType.isAssignableFrom(baseValue.getClass())) {
        	if(Set.class.isAssignableFrom(fieldType)) {
        		result = new HashSet((Collection) baseValue);
        	} else if(List.class.isAssignableFrom(fieldType)) {
        		result = new ArrayList((Collection) baseValue);
        	}
        }
        
        return result;
    }
}

class ConstructorArgumentProcessor extends ValueProcessor {
    private final Map<String, Object> parameters;
    
    ConstructorArgumentProcessor(final Map<String, Object> parameters) {
        this.parameters = parameters;
    }

    @Override
    protected String getValue(String parameterName) {
        return parameters.get(parameterName).toString();
    }
}

class PropertyProcessor extends ValueProcessor {
    private final Object result;
    
    PropertyProcessor(final Object result) {
        this.result = result;
    }

    @Override
    protected String getValue(String propertyName) {
        return ReflectionUtils.invokeRecursiveGetter(result, propertyName).toString();
    }
}