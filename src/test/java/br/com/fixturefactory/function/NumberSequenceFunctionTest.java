package br.com.fixturefactory.function;

import static junit.framework.Assert.assertEquals;

import org.junit.Test;

public class NumberSequenceFunctionTest {

	@Test
	public void intSequence() {
		SequenceFunction function = new SequenceFunction(new NumberSequence(0, 1));
		
		for (int i=0; i<3; i++) {
			assertEquals("integers should be equal", function.generateValue(), i);
		}
	}
	
	@Test
	public void longSequence() {
		SequenceFunction function = new SequenceFunction(new NumberSequence(1L, 2));
		
		for (int i=1; i<=5; i=i+2) {
			assertEquals("longs should be equal", function.generateValue(), (long) i);
		}
	}
	
	@Test
	public void floatSequence() {
		SequenceFunction function = new SequenceFunction(new NumberSequence(1.2f, 1));
		
		for (int i=1; i<=3; i++) {
			assertEquals("floats should be equal", function.generateValue(), (float) i+(.2F));
		}
	}
	
	@Test
	public void doubleSequence() {
		SequenceFunction function = new SequenceFunction(new NumberSequence(1.23d, 2));
		
		for (int i=1; i<=5; i=i+2) {
			assertEquals("doubles should be equal", function.generateValue(), (double) i+(.23d));
		}
	}
}
