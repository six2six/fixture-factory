package br.com.six2six.fixturefactory.function;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;

import br.com.six2six.fixturefactory.function.impl.DateAsStringFunction;

public class DateAsStringFunctionTest {
	
	@Test
	public void shouldFormatDateWithGivenPattern() throws ParseException {
		String dateString = "02/06/1991";
		String pattern = "dd/MM/yyyy";
		SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
		Date date = dateFormat.parse(dateString);
		
		DateFunction dateFunction = mock(DateFunction.class);
		when(dateFunction.generateValue()).thenReturn(date);
		DateAsStringFunction dateAsStringFunction = new DateAsStringFunction(dateFunction, pattern);
		
		assertEquals(dateString, dateAsStringFunction.generateValue());
	}

}
