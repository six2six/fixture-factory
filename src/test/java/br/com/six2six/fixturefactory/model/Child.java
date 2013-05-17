package br.com.six2six.fixturefactory.model;

import java.io.Serializable;

public class Child extends Parent implements Serializable {

    private static final long serialVersionUID = -1673362240275825323L;
 
    private String childAttribute;
	
    public Child(Attribute parentAttribute, String childAttribute) {
        super(parentAttribute);
        this.childAttribute = childAttribute;
    }
	
    public String getChildAttribute() {
        return this.childAttribute;
    }
}
