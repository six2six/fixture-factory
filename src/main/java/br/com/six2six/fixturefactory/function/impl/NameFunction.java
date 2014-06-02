package br.com.six2six.fixturefactory.function.impl;

import br.com.six2six.bfgex.Gender;
import br.com.six2six.bfgex.RandomGen;
import br.com.six2six.fixturefactory.function.AtomicFunction;

public class NameFunction implements AtomicFunction {

	private Gender gender;
	
	private NameType type;
	
	public NameFunction() { }
	
	public NameFunction(Gender gender) {
		this.gender = gender;
	}

	public NameFunction(NameType type) {
		this.type = type;
	}

	public NameFunction(NameType type, Gender gender) {
		this(type);
		this.gender = gender;
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T> T generateValue() {
		Object value = null;
		
		if (this.type != null && type == NameType.FIRST) {
			value = this.gender != null ? RandomGen.firstName(this.gender) : RandomGen.firstName();
			
		} else if (this.type != null && type == NameType.LAST) {
			value = RandomGen.lastName();
			
		} else if (this.gender != null) {
			value = RandomGen.name(this.gender);
			
		} else {
			value = RandomGen.name();
		}
		
		return (T) value;
	}

	public enum NameType {
		FIRST, LAST
	}
	
}

