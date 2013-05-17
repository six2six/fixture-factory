package br.com.six2six.fixturefactory.model;

import java.util.Date;

public class Immutable {

	private String propertyA;
	
	private Long propertyB;
	
	private String propertyC;
	
	private Date date;
	
	private Address address;
	
	private ImmutableInner immutableInner;
	
	public Immutable(String propertyA, Long propertyB) {
		this(propertyA, propertyB, "default", null, null);
	}
	
	public Immutable(Long propertyB, String propertyC, Address address) {
		this("default", propertyB, propertyC, null, address);
	}

	public Immutable(String propertyA, Long propertyB, String propertyC, Date date, Address address) {
		this.propertyA = propertyA;
		this.propertyB = propertyB;
		this.propertyC = propertyC;
		this.date = date;
		this.address = address;
	}

	public String getPropertyA() {
		return propertyA;
	}

	public Long getPropertyB() {
		return propertyB;
	}

	public String getPropertyC() {
		return propertyC;
	}
	
	public class ImmutableInner {
		private String propertyD;

		public ImmutableInner(String propertyD) {
			this.propertyD = propertyD;
		}

		public String getPropertyD() {
			return propertyD;
		}
	}

	public ImmutableInner getImmutableInner() {
		return immutableInner;
	}

	public void setImmutableInner(ImmutableInner immutableInner) {
		this.immutableInner = immutableInner;
	}

	public Date getDate() {
		return date;
	}

	public Address getAddress() {
		return address;
	}
}
