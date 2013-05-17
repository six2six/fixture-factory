package br.com.six2six.fixturefactory.base;

public class Range {

	private Number start;
	
	private Number end;

	public Range(Number start, Number end) {
		if (start.doubleValue() >= end.doubleValue()) {
			throw new IllegalArgumentException(String.format("incorrect range: [%d, %d]", start, end));
		}
		
		this.start = start;
		this.end = end;
	}

	public Number getStart() {
		return start;
	}

	public Number getEnd() {
		return end;
	}
	
}
