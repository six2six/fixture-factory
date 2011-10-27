package br.com.fixturefactory.function;

import br.com.fixturefactory.base.Range;

public class CpfFunction implements Function {

	private boolean formatted;
	
	public CpfFunction() { }
	
	public CpfFunction(boolean formatted) {
		this.formatted = formatted;
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T> T generateValue() {
		RandomFunction random = new RandomFunction(Integer.class, new Range(1, 9));

		Integer a = random.generateValue();
		Integer b = random.generateValue();
		Integer c = random.generateValue();
		Integer d = random.generateValue();
		Integer e = random.generateValue();
		Integer f = random.generateValue();
		Integer g = random.generateValue();
		Integer h = random.generateValue();
		Integer i = random.generateValue();

		int j = (a * 10 + b * 9 + c * 8 + d * 7 + e * 6 + f * 5 + g * 4 + h * 3 + i * 2);
		j = j % 11;

		j = j <= 1? 0 : 11 - j;

		int m = (a * 11 + b * 10 + c * 9 + d * 8 + e * 7 + f * 6 + g * 5 + h * 4 + i * 3 + j * 2);
		m = m % 11;

		m = m <= 1? 0 : 11 - m;

		return (T) String.format(formatted? "%d%d%d.%d%d%d.%d%d%d-%d%d" : "%d%d%d%d%d%d%d%d%d%d%d", a, b, c, d, e, f, g, h, i, j, m);
	}

}
