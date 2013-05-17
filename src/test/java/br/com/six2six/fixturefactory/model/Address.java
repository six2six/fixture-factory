package br.com.six2six.fixturefactory.model;

import java.io.Serializable;

public class Address implements Serializable {

	private static final long serialVersionUID = -157590924427802878L;

	private Long id;
	private String street;
	private String city;
	private String state;
	private String country;
	private String zipCode;

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getZipCode() {
		return zipCode;
	}
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	@Override
	public String toString() {
		return "Address [city=" + city + ", country=" + country + ", id=" + id + ", state=" + state + ", street=" + street + ", zipCode=" + zipCode + "]";
	}
	
}
