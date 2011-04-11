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

import br.com.bfgex.Gender;
import br.com.fixturefactory.function.CalendarInterval;
import br.com.fixturefactory.function.CalendarSequence;
import br.com.fixturefactory.function.DateTimeFunction;
import br.com.fixturefactory.function.FixtureFunction;
import br.com.fixturefactory.function.Function;
import br.com.fixturefactory.function.NameFunction;
import br.com.fixturefactory.function.NumberSequence;
import br.com.fixturefactory.function.RandomFunction;
import br.com.fixturefactory.function.Range;
import br.com.fixturefactory.function.RegexFunction;
import br.com.fixturefactory.function.Sequence;
import br.com.fixturefactory.function.SequenceFunction;
import br.com.fixturefactory.util.DateTimeUtil;

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
	
	public CalendarSequence startWith(String base, DateFormat format, CalendarInterval interval) {
		return new CalendarSequence(DateTimeUtil.toCalendar(base, format), interval);
	}
	
	public CalendarInterval incrementBy(int amount, int calendarField) {
		return new CalendarInterval(amount, calendarField);
	}
	
	public CalendarInterval decrementBy(int amount, int calendarField) {
		return new CalendarInterval(amount*(-1), calendarField);
	}
	
	public Sequence<Number> startWith(Number start, int amount) {
		return new NumberSequence(start, amount);
	}
	
	public int incrementBy(int amount) {
		return amount;
	}
	
	public int decrementBy(int amount) {
		return amount*(-1);
	}
	
	public Set<Property> getProperties() {
		return properties;
	}
}
