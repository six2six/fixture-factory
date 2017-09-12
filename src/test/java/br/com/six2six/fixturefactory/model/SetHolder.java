package br.com.six2six.fixturefactory.model;

import java.util.LinkedHashSet;

import java.io.Serializable;

public class SetHolder implements Serializable {

    private static final long serialVersionUID = 7764593702512737207L;

    private LinkedHashSet<Attribute> attributes;

    public LinkedHashSet<Attribute> getAttributes() {
        return this.attributes;
    }

    public void setAttributes(LinkedHashSet<Attribute> attributes) {
        this.attributes = attributes;
    }
}
