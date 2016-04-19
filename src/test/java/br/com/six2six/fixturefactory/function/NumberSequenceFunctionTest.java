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
			assertEquals(((Integer)function.generateValue()).intValue(), i);
		}
	}
	
	@Test
	public void longSequence() {
		SequenceFunction function = new SequenceFunction(new NumberSequence(1L, 2));
		
		for (int i=1; i<=5; i=i+2) {
			assertEquals("longs should be equal", ((Long)function.generateValue()).longValue(), (long) i);
		}
	}
	
	@Test
	public void floatSequence() {
		SequenceFunction function = new SequenceFunction(new NumberSequence(1.2f, 1));
		
		for (int i=1; i<=3; i++) {
			assertEquals("floats should be equal", ((Float)function.generateValue()).floatValue(), (float) i+(.2F));
		}
	}
	
	@Test
	public void doubleSequence() {
		SequenceFunction function = new SequenceFunction(new NumberSequence(1.23d, 2));
		
		for (int i=1; i<=5; i=i+2) {
			assertEquals("doubles should be equal", ((Double)function.generateValue()).doubleValue(), (double) i+(.23d));
		}
	}
}
