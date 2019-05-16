package br.com.six2six.fixturefactory.util;

public class EnumUtils {

    public static String getText(Enum<?> value) {
        return value != null ? value.toString():"";
    }
}
