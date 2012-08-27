package br.com.fixturefactory.model;

import java.util.List;

public class Employee {

	private List<Position> positions;
	
	private String name;
	
	private List<Client> clients;

	public Employee() {	}
	
	public Employee(List<Position> position, String name) {
		super();
		this.positions = position;
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Position> getPositions() {
		return positions;
	}

	public void setPositions(List<Position> positions) {
		this.positions = positions;
	}

	public List<Client> getClients() {
		return clients;
	}

	public void setClients(List<Client> clients) {
		this.clients = clients;
	}
	
}
