package br.com.six2six.fixturefactory.function.impl;

import br.com.six2six.fixturefactory.base.Range;
import br.com.six2six.fixturefactory.function.AtomicFunction;

public class CnpjFunction implements AtomicFunction {

	private boolean formatted;
	
	public CnpjFunction() { }
	
	public CnpjFunction(boolean formatted) {
		this.formatted = formatted;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public <T> T generateValue() {
		RandomFunction random = new RandomFunction(Integer.class, new Range(1, 9));
		Integer a  = random.generateValue();
		Integer b  = random.generateValue();
		Integer c  = random.generateValue();
		Integer d  = random.generateValue();
		Integer e  = random.generateValue();
		Integer f  = random.generateValue();
		Integer g  = random.generateValue();
		Integer h  = random.generateValue();
		Integer i  = 0;
		Integer j = 0;
		Integer l = 0;
		Integer m = 1;
		
		int n = m*2+l*3+j*4+i*5+h*6+g*7+f*8+e*9+d*2+c*3+b*4+a*5;
		n = n % 11 < 2 ? 0 : 11 - (n % 11);

		int o = n*2+m*3+l*4+j*5+i*6+h*7+g*8+f*9+e*2+d*3+c*4+b*5+a*6;
		o = o % 11 < 2 ? 0 : 11 - (o % 11);
		
		return (T) String.format(formatted? "%d%d.%d%d%d.%d%d%d/%d%d%d%d-%d%d" : "%d%d%d%d%d%d%d%d%d%d%d%d%d%d", a, b, c, d, e, f, g, h, i, j, l, m, n, o);
	}
}
