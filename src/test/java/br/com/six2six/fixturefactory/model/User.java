package br.com.six2six.fixturefactory.model;

import java.io.Serializable;
import java.util.List;

import br.com.bfgex.Gender;

public class User implements Serializable {

	private static final long serialVersionUID = 6276678114928905392L;

	private String name;
	private String login;
	private String password;
	private Gender gender;
	private String email;
	private List<UserType> userTypes;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Gender getGender() {
		return gender;
	}
	public void setGender(Gender gender) {
		this.gender = gender;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public List<UserType> getUserTypes() {
		return userTypes;
	}
	public void setUserTypes(List<UserType> userTypes) {
		this.userTypes = userTypes;
	}
	
}
