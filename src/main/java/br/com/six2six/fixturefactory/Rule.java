package br.com.six2six.fixturefactory;

import br.com.six2six.bfgex.Gender;
import br.com.six2six.fixturefactory.base.*;
import br.com.six2six.fixturefactory.function.*;
import br.com.six2six.fixturefactory.function.impl.*;
import br.com.six2six.fixturefactory.util.ReflectionUtils;
import com.mdimension.jchronic.Options;

import java.math.BigDecimal;
import java.math.MathContext;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

import static br.com.six2six.fixturefactory.function.impl.DateTimeFunction.DateType.AFTER;
import static br.com.six2six.fixturefactory.function.impl.DateTimeFunction.DateType.BEFORE;
import static br.com.six2six.fixturefactory.function.impl.NameFunction.NameType.FIRST;
import static br.com.six2six.fixturefactory.function.impl.NameFunction.NameType.LAST;
import static br.com.six2six.fixturefactory.util.DateTimeUtils.toCalendar;

public class Rule {

	public Rule() {}

	public Rule(Rule baseRule, Rule extendedRule) {
		properties = new LinkedHashSet<Property>(baseRule.getProperties());
		for(Property property : extendedRule.getProperties()) {
			properties.remove(property);
			properties.add(property);
		}
	}
	
    private Set<Property> properties = new LinkedHashSet<Property>();

    public void add(String property, Object value) {
        this.properties.add(new Property(property, value));
    }

    public void add(String property, Function function) {
        if (function == null) {
            function = new IdentityFunction(null);
        }
    	this.properties.add(new Property(property, function));
    }
    
    /**
     * @deprecated use {@link one(clazz, label)} instead.
     */
    @Deprecated
	public Function fixture(Class<?> clazz, String label) {
    	return new FixtureFunction(clazz, label);
    }

    /**
     * @deprecated use {@link has(quantity).of(clazz, label)} instead.
     */
    @Deprecated
	public Function fixture(Class<?> clazz, Integer quantity, String label) {
    	return new FixtureFunction(clazz, label, quantity);
    }
	
	public Chainable has(int quantity) {
		return new AssociationFunctionImpl(quantity);
	}
	
	public AssociationFunction one(Class<?> clazz, String label) {
		return new AssociationFunctionImpl(clazz, label);
	}
	
	public Function random(Class<?> clazz, Object... dataset) {
		return new RandomFunction(clazz, dataset);
	}

	public Function random(Class<? extends BigDecimal> clazz, MathContext mc) {
	    return new RandomFunction(clazz, mc);
	}

	public Function random(Object... dataset) {
		return new RandomFunction(dataset);
	}
	
	public Function random(Class<?> clazz, Range range) {
		return new RandomFunction(clazz, range);
	}
	
	public Function uniqueRandom(int minValue, int maxValue) {
		return new UniqueRandomFunction(minValue, maxValue);
	}
	
	public Function uniqueRandom(Object... dataset) {
		return new UniqueRandomFunction(dataset);
	}
	
	public Function uniqueRandom(Class<? extends Enum<?>> clazz) {
		return new UniqueRandomFunction(clazz);
	}

	public Function name() {
		return new NameFunction();
	}

	public Function name(Gender gender) {
		return new NameFunction(gender);
	}

	public Function firstName() {
		return new NameFunction(FIRST);
	}

	public Function firstName(Gender gender) {
		return new NameFunction(FIRST, gender);
	}
	
	public Function lastName() {
		return new NameFunction(LAST);
	}

	public Function beforeDate(String source, SimpleDateFormat format) {
		return new DateTimeFunction(toCalendar(source, format), BEFORE);
	}
	
	public Function afterDate(String source, SimpleDateFormat format) {
		return new DateTimeFunction(toCalendar(source, format), AFTER);
	}
	
	public Function randomDate(String startDate, String endDate, DateFormat format) {
		return new DateTimeFunction(toCalendar(startDate, format), toCalendar(endDate, format));
	}

	public Function regex(String regex) {
		return new RegexFunction(regex);
	}
	
	public Range range(Number start, Number end) {
		return new Range(start, end);
	}

	public Function sequence(Sequence<?> sequence) {
		return new SequenceFunction(sequence);
	}

	public Function sequence(Number startWith, int incrementBy) {
		return new SequenceFunction(new NumberSequence(startWith, incrementBy));
	}
	
	public Function sequence(Class<? extends Number> clazz) {
		Number number = ReflectionUtils.newInstance(clazz, Arrays.asList("1"));
		return new SequenceFunction(new NumberSequence(number, 1));
	}
	
	public Function sequenceDate(String base, CalendarInterval interval) {
		return this.sequenceDate(base, new SimpleDateFormat("yyyy-MM-dd"), interval);
	}
	
	public Function sequenceDate(String base, DateFormat simpleDateFormat, CalendarInterval interval) {
		return new SequenceFunction(new CalendarSequence(toCalendar(base, simpleDateFormat), interval));
	}
	
	public DateFunction instant(String dateText) {
	    return new ChronicFunction(dateText);
	}

    public Function instant(String dateText, Options options) {
        return new ChronicFunction(dateText, options);
    }
    
    public Function cnpj() {
    	return new CnpjFunction();
    }
    
    public Function cnpj(boolean formatted) {
    	return new CnpjFunction(formatted);
    }

    public Function cpf() {
        return new CpfFunction();
    }

    public Function cpf(boolean formatted) {
        return new CpfFunction(formatted);
    }
	
	public Interval increment(int interval) {
		return new Interval(interval);
	}
	
	public Interval decrement(int interval) {
		return new Interval(interval*(-1));
	}
	
	public Set<Property> getProperties() {
		return properties;
	}
}
