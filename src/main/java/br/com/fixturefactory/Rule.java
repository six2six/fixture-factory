package br.com.fixturefactory;

import static br.com.fixturefactory.function.DateTimeFunction.DateType.AFTER;
import static br.com.fixturefactory.function.DateTimeFunction.DateType.BEFORE;
import static br.com.fixturefactory.function.NameFunction.NameType.FIRST;
import static br.com.fixturefactory.function.NameFunction.NameType.LAST;
import static br.com.fixturefactory.util.DateTimeUtil.toCalendar;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

import br.com.bfgex.Gender;
import br.com.fixturefactory.base.CalendarInterval;
import br.com.fixturefactory.base.CalendarSequence;
import br.com.fixturefactory.base.Interval;
import br.com.fixturefactory.base.Range;
import br.com.fixturefactory.base.Sequence;
import br.com.fixturefactory.function.AssociationFunction;
import br.com.fixturefactory.function.ChronicFunction;
import br.com.fixturefactory.function.DateTimeFunction;
import br.com.fixturefactory.function.FixtureFunction;
import br.com.fixturefactory.function.Function;
import br.com.fixturefactory.function.NameFunction;
import br.com.fixturefactory.function.NumberSequence;
import br.com.fixturefactory.function.RandomFunction;
import br.com.fixturefactory.function.RegexFunction;
import br.com.fixturefactory.function.SequenceFunction;
import br.com.fixturefactory.util.Chainable;
import br.com.fixturefactory.util.ReflectionUtils;

import com.mdimension.jchronic.Options;

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
		return new AssociationFunction(quantity);
	}
	
	public AssociationFunction one(Class<?> clazz, String label) {
		return new AssociationFunction(clazz, label);
	}
	
	public Function one(Class<?> clazz, String label, String targetAttribute) {
		return new AssociationFunction(clazz, label, targetAttribute);
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
	
	public Function instant(String dateText) {
	    return new ChronicFunction(dateText);
	}

    public Function instant(String dateText, Options options) {
        return new ChronicFunction(dateText, options);
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
