package br.com.six2six.template;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import br.com.six2six.fixturefactory.model.City;
import br.com.six2six.fixturefactory.model.Neighborhood;
import br.com.six2six.fixturefactory.model.Route;
import br.com.six2six.fixturefactory.model.RouteId;

public class RouteTemplate implements TemplateLoader {

	@Override
	public void load() {
		Fixture.of(Route.class).addTemplate("routeWithDifferentCities", new Rule() {{
			add("id", one(RouteId.class, "valid"));
    		add("cities", has(2).of(City.class, "saoPaulo", "rioDeJaneiro"));
		}});
		
		Fixture.of(City.class).addTemplate("saoPaulo", new Rule() {{
	        add("name", "São Paulo");
	        add("neighborhoods", has(2).of(Neighborhood.class, "valid"));
	    }}).addTemplate("rioDeJaneiro", new Rule() {{
	    	add("name", "Rio de Janeiro");
	        add("neighborhoods", has(2).of(Neighborhood.class, "valid"));
	    }});
	}
	
}
