package br.com.fixturefactory.model;

import java.io.Serializable;
import java.util.Calendar;

public class Client implements Serializable {

	private static final long serialVersionUID = 6875996936038420202L;

	private Long id;
	private String name;
	private String nickname;
	private String email;
	private Calendar birthday;
	private Address address;
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getNickname() {
		return nickname;
	}
	
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public Calendar getBirthday() {
		return birthday;
	}
	
	public void setBirthday(Calendar birthday) {
		this.birthday = birthday;
	}
	
	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	@Override
	public String toString() {
		return "Client [address=" + address + ", birthday=" + birthday + ", email=" + email + ", id=" + id + ", name=" + name + ", nickname=" + nickname + "]";
	}

}
