package br.com.six2six.fixturefactory.model;

import java.io.Serializable;
import java.util.List;

public class City implements Serializable {
	
	private static final long serialVersionUID = -4759361048419836513L;
	
	private String name;
	private List<Neighborhood> neighborhoods;

	public City(String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}

	public List<Neighborhood> getNeighborhoods() {
		return neighborhoods;
	}

	public void setNeighborhoods(List<Neighborhood> neighborhoods) {
		this.neighborhoods = neighborhoods;
	}
	
}
