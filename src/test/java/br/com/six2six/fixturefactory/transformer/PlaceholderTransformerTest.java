package br.com.six2six.fixturefactory.transformer;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import br.com.six2six.fixturefactory.model.User;

public class PlaceholderTransformerTest {

    @Test
    public void shouldTransformParameterPlaceholder() {
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("name", "someone");
        
        String result = new ParameterPlaceholderTransformer(parameters).transform("${name}@domain.com", String.class);
        assertEquals("someone@domain.com", result);
    }
    
    @Test
    public void shouldTransformPropertyPlaceholder() {
        User user = new User();
        user.setName("someone");
        
        String result = new PropertyPlaceholderTransformer(user).transform("${name}@domain.com", String.class);
        assertEquals("someone@domain.com", result);
    }
    
    @Test
    public void shouldNotTransformWithoutPlaceholder() {
        User user = new User();
        user.setName("someone");
        
        String result = new PropertyPlaceholderTransformer(user).transform("name@domain.com", String.class);
        assertEquals("name@domain.com", result);
    }
}
