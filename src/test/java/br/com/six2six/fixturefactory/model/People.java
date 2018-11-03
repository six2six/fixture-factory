package br.com.six2six.fixturefactory.model;

import java.io.Serializable;
import java.time.LocalDate;

public class People implements Serializable{

	private static final long serialVersionUID = -9071464054942141370L;

	private Integer age;
	
	private Long doc;
	
	private String name;
	
	private LocalDate birth;

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public Long getDoc() {
		return doc;
	}

	public void setDoc(Long doc) {
		this.doc = doc;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LocalDate getBirth() {
		return birth;
	}

	public void setBirth(LocalDate birth) {
		this.birth = birth;
	}
	
}