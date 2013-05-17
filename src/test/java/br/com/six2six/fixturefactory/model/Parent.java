package br.com.six2six.fixturefactory.model;

import java.io.Serializable;

public class Parent implements Serializable {

    private static final long serialVersionUID = -3357965282533662738L;

    private Attribute parentAttribute;

    public Parent(Attribute parentAttribute) {
        this.parentAttribute = parentAttribute;
    }

    public Attribute getParentAttribute() {
        return this.parentAttribute;
    }
}
