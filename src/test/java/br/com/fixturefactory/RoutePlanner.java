package br.com.fixturefactory;

import java.io.Serializable;

import br.com.fixturefactory.model.Route;

public class RoutePlanner implements Serializable {

	private static final long serialVersionUID = 5980833939561010471L;
	
	private Route route;

	public RoutePlanner(Route route) {
		this.route = route;
	}

	public Route getRoute() {
		return route;
	}
}
