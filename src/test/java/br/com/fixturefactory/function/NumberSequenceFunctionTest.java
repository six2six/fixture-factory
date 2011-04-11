package br.com.fixturefactory.function;

import junit.framework.Assert;

import org.junit.Test;

public class NumberSequenceFunctionTest {

	@Test
	public void intSequence() {
		SequenceFunction function = new SequenceFunction(new NumberSequence(0, 1));
		
		for (int i=0; i<3; i++) {
			Assert.assertEquals("integers should be equal", function.generateValue(), i);
		}
	}
	
	@Test
	public void longSequence() {
		SequenceFunction function = new SequenceFunction(new NumberSequence(1L, 2));
		
		for (int i=1; i<=5; i=i+2) {
			Assert.assertEquals("longs should be equal", function.generateValue(), (long) i);
		}
	}
	
	@Test
	public void floatSequence() {
		SequenceFunction function = new SequenceFunction(new NumberSequence(1.2f, 1));
		
		for (int i=1; i<=3; i++) {
			Assert.assertEquals("floats should be equal", function.generateValue(), (float) i+(.2F));
		}
	}
	
	@Test
	public void doubleSequence() {
		SequenceFunction function = new SequenceFunction(new NumberSequence(1.23d, 2));
		
		for (int i=1; i<=5; i=i+2) {
			Assert.assertEquals("doubles should be equal", function.generateValue(), (double) i+(.23d));
		}
	}
}
