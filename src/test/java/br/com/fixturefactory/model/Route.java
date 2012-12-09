package br.com.fixturefactory.model;

import java.io.Serializable;
import java.util.List;

public class Route implements Serializable {
	
	private static final long serialVersionUID = -3423798600729783144L;

	private RouteId id;
	
	private List<City> cities;

	public Route(RouteId id, List<City> cities) {
		this.id = id;
		this.cities = cities;
	}
	
	public RouteId getId() {
		return id;
	}

	public List<City> getCities() {
		return this.cities;
	}
}
