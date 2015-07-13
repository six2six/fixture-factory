package br.com.six2six.fixturefactory.function;

import java.util.Calendar;

import org.junit.Assert;
import org.junit.Test;

import br.com.six2six.fixturefactory.function.impl.ChronicFunction;

import com.mdimension.jchronic.Options;
import com.mdimension.jchronic.utils.Time;

public class ChronicFunctionTest {
    
    @Test
    public void testChronic() {
        ChronicFunction chronicFunction = new ChronicFunction("yesterday", new Options(Time.construct(2011, 10, 31, 14, 0, 0, 0)));
        Assert.assertEquals(Time.construct(2011, 10, 30, 12).getTime(), ((Calendar) chronicFunction.generateValue()).getTime());
    }

}
