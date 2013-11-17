package br.com.six2six.fixturefactory;

import br.com.six2six.fixturefactory.function.AtomicFunction;
import br.com.six2six.fixturefactory.function.Function;
import br.com.six2six.fixturefactory.function.IdentityFunction;
import br.com.six2six.fixturefactory.function.RelationFunction;
import br.com.six2six.fixturefactory.processor.Processor;

public class Property {

	private String name;
	
	private Function function;

	public Property(String name, Function function) {
	    if (name == null) {
	        throw new IllegalArgumentException("name must not be null");
	    }
	    
	    if (function == null) {
            throw new IllegalArgumentException("function must not be null");
        }
	    
		this.name = name;
		this.function = function;
	}

	public Property(String name, Object value) {
	    this(name, new IdentityFunction(value));
	}

	public String getName() {
		return this.name;
	}

	public Object getValue() {
		return ((AtomicFunction) this.function).generateValue();
	}
	
	public Object getValue(Processor processor) {
		return ((RelationFunction) this.function).generateValue(processor);
	}
	
	public Object getValue(Object owner) {
		return ((RelationFunction) this.function).generateValue(owner);
	}
	
	public Object getValue(Object owner, Processor processor) {
		return ((RelationFunction) this.function).generateValue(owner, processor);
	}
	
	public boolean hasRelationFunction() {
		return this.function instanceof RelationFunction;
	}

	public String getRootAttribute() {
		int index = this.name.indexOf(".");
		return index > 0 ? this.name.substring(0, index) : this.name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Property other = (Property) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
}
