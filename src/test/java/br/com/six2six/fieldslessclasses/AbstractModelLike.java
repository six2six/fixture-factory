package br.com.six2six.fieldslessclasses;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractModelLike {
    protected Map<String,Object> attributes;

    public AbstractModelLike() {
        attributes = new HashMap<>();
    }
}
