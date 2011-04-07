package br.com.fixturefactory.function;

import br.com.bfgex.Gender;
import br.com.bfgex.Randgen;

public class NameFunction implements Function {

	private Gender gender;
	
	private Type type;
	
	public NameFunction() { }
	
	public NameFunction(Gender gender) {
		this.gender = gender;
	}

	public NameFunction(Type type) {
		this.type = type;
	}

	public NameFunction(Type type, Gender gender) {
		this(type);
		this.gender = gender;
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T> T generateValue() {
		Object value = null;
		
		if (this.type != null && type == Type.FIRST) {
			value = this.gender != null ? Randgen.firstName(this.gender) : Randgen.firstName();
			
		} else if (this.type != null && type == Type.LAST) {
			value = Randgen.lastName();
			
		} else if (this.gender != null) {
			value = Randgen.name(this.gender);
			
		} else {
			value = Randgen.name();
		}
		
		return (T) value;
	}

	public enum Type {
		FIRST, LAST
	}
	
}

