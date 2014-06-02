package br.com.six2six.fixturefactory.function.impl;

import br.com.six2six.bfgex.RegexGen;
import br.com.six2six.fixturefactory.function.AtomicFunction;

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
