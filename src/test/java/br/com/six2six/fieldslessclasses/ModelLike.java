package br.com.six2six.fieldslessclasses;

public class ModelLike extends AbstractModelLike {

    private static final String CODE = "code";
    private static final String NUMBER = "number";
    private Boolean aBoolean;

    public String getCode() {
        return (String) this.attributes.get(CODE);
    }

    public Integer getNumber() {
        return (Integer) this.attributes.get(NUMBER);
    }

    public void setCode(String code) {
        this.attributes.put(CODE,code);
    }

    public void setNumber(Integer number) {
        this.attributes.put(NUMBER,number);
    }

    public Boolean getaBoolean() {
        return aBoolean;
    }

    public void setaBoolean(Boolean aBoolean) {
        this.aBoolean = aBoolean;
    }
}

