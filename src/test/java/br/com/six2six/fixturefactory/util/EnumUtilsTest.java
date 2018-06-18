package br.com.six2six.fixturefactory.util;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class EnumUtilsTest {

    @Test
    public void shouldGetEnumText(){
        String textA = EnumUtils.getText(MyEnum.VALUE_A);
        String textB = EnumUtils.getText(MyEnum.VALUE_B);
        String textC = EnumUtils.getText(MyEnum.VALUE_C);

        assertEquals("Text and Enum should be equal", textA, "VALUE_A");
        assertEquals("Text and Enum should be equal", textB, "VALUE_B");
        assertEquals("Text and Enum should be equal", textC, "VALUE_C");
    }
    
    @Test
    public void shouldGetBlankWhenNull(){
        String textA = null;
        
        assertNotEquals("Text and Enum not equals", textA, "");
    }

    enum MyEnum {
        VALUE_A,
        VALUE_B,
        VALUE_C
    }

}
