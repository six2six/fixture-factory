package br.com.six2six.fixturefactory.util;

public enum Accessor {
    GETTER("get"),
    SETTER("set");

    private String code;

    private Accessor(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
