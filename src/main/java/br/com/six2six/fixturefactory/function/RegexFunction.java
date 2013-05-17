package br.com.six2six.fixturefactory.function;

import br.com.six2six.bfgex.RegexGen;

public class RegexFunction implements AtomicFunction {

	private String pattern;
	
	public RegexFunction(String pattern) {
		this.pattern = pattern;
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T> T generateValue() {
		return (T) RegexGen.of(pattern);
	}

}
