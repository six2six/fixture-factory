package br.com.fixturefactory;

import static br.com.fixturefactory.function.DateTimeFunction.DateType.AFTER;
import static br.com.fixturefactory.function.DateTimeFunction.DateType.BEFORE;
import static br.com.fixturefactory.function.NameFunction.NameType.FIRST;
import static br.com.fixturefactory.function.NameFunction.NameType.LAST;
import static br.com.fixturefactory.util.DateTimeUtil.toCalendar;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.LinkedHashSet;
import java.util.Set;

import com.mdimension.jchronic.Options;

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
	
	public Chainable has(int quantity) {
		return new AssociationFunction(new FixtureFunction(null, null, quantity));
	}
	
	public AssociationFunction one(Class<?> clazz, String label) {
		return new AssociationFunction(new FixtureFunction(clazz, label));
	}
	
	public Function one(Class<?> clazz, String label, String targetAttribute) {
		return new AssociationFunction(new FixtureFunction(clazz, label), targetAttribute);
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
