package br.com.fixturefactory.model;

import java.io.Serializable;

import br.com.bfgex.Gender;

public class Student implements Serializable {

	private static final long serialVersionUID = 1659316546025160752L;

	private Long id;
	private String firstName;
	private String lastName;
	private Gender gender;

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public Gender getGender() {
		return gender;
	}
	public void setGender(Gender gender) {
		this.gender = gender;
	}
	
}
