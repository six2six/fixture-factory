package br.com.six2six.fixturefactory.function;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import br.com.six2six.fixturefactory.function.impl.NumberSequence;
import br.com.six2six.fixturefactory.function.impl.SequenceFunction;

public class NumberSequenceFunctionTest {

	@Test
	public void intSequence() {
		SequenceFunction function = new SequenceFunction(new NumberSequence(0, 1));
		
		for (int i=0; i<3; i++) {
			Integer value = Integer.valueOf(i);
			assertEquals("integers should be equal", function.generateValue(), value);
		}
	}
	
	@Test
	public void longSequence() {
		SequenceFunction function = new SequenceFunction(new NumberSequence(1L, 2));
		
		for (int i=1; i<=5; i=i+2) {
			Long value = Long.valueOf(i);
			assertEquals("longs should be equal", function.generateValue(), value);
		}
	}
	
	@Test
	public void floatSequence() {
		SequenceFunction function = new SequenceFunction(new NumberSequence(1.2f, 1));
		
		for (int i=1; i<=3; i++) {
			Float value = Float.valueOf(i+(.2F));
			assertEquals("floats should be equal", function.generateValue(), value);
		}
	}
	
	@Test
	public void doubleSequence() {
		SequenceFunction function = new SequenceFunction(new NumberSequence(1.23d, 2));
		
		for (int i=1; i<=5; i=i+2) {
			Double value = Double.valueOf(i+(.23d));
			assertEquals("doubles should be equal", function.generateValue(), value);
		}
	}
}
