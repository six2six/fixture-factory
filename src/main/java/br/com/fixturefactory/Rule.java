package br.com.fixturefactory;

import java.util.LinkedHashSet;
import java.util.Set;

import br.com.bfgex.Gender;
import br.com.fixturefactory.function.FixtureFunction;
import br.com.fixturefactory.function.Function;
import br.com.fixturefactory.function.NameFunction;
import br.com.fixturefactory.function.NameFunction.Type;
import br.com.fixturefactory.function.RandomFunction;
import br.com.fixturefactory.function.Range;

public class Rule {

    private Set<Property> properties = new LinkedHashSet<Property>();
	
    public void add(String property, Object value) {
        this.properties.add(new Property(property, value));
    }

    public void add(String property, Function function) {
    	this.properties.add(new Property(property, function));
    }

	public Function fixture(Class<?> clazz, String label) {
    	return new FixtureFunction(clazz, label);
    }

	public Function fixture(Class<?> clazz, Integer quantity, String label) {
    	return new FixtureFunction(clazz, label, quantity);
    }
	
	public Function random(Class<?> clazz, Object... dataset) {
		return new RandomFunction(clazz, dataset);
	}

	public Function random(Object... dataset) {
		return new RandomFunction(dataset);
	}
	
	public Function random(Class<?> clazz, Range range) {
		return new RandomFunction(clazz, range);
	}

	public Function name() {
		return new NameFunction();
	}

	public Function name(Gender gender) {
		return new NameFunction(gender);
	}

	public Function firstName() {
		return new NameFunction(Type.FIRST);
	}

	public Function firstName(Gender gender) {
		return new NameFunction(Type.FIRST, gender);
	}
	
	public Function lastName() {
		return new NameFunction(Type.LAST);
	}

	public Range range(Number start, Number end) {
		return new Range(start, end);
	}

	public Set<Property> getProperties() {
		return properties;
	}

}
