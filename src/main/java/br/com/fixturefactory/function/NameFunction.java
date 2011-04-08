package br.com.fixturefactory.function;

import br.com.bfgex.Gender;
import br.com.bfgex.Randgen;

public class NameFunction implements Function {

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
			value = this.gender != null ? Randgen.firstName(this.gender) : Randgen.firstName();
			
		} else if (this.type != null && type == NameType.LAST) {
			value = Randgen.lastName();
			
		} else if (this.gender != null) {
			value = Randgen.name(this.gender);
			
		} else {
			value = Randgen.name();
		}
		
		return (T) value;
	}

	public enum NameType {
		FIRST, LAST
	}
	
}

