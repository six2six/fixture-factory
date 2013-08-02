package br.com.six2six.template;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import br.com.six2six.fixturefactory.model.Address;
import br.com.six2six.fixturefactory.model.Attribute;
import br.com.six2six.fixturefactory.model.Child;
import br.com.six2six.fixturefactory.model.City;
import br.com.six2six.fixturefactory.model.Immutable;
import br.com.six2six.fixturefactory.model.Immutable.ImmutableInner;
import br.com.six2six.fixturefactory.model.Route;
import br.com.six2six.fixturefactory.model.RouteId;
import br.com.six2six.fixturefactory.model.RoutePlanner;

public class ImmutableTemplate implements TemplateLoader {

	@Override
	public void load() {
		Fixture.of(Immutable.class)
		.addTemplate("twoParameterConstructor", new Rule(){{
			add("propertyA", regex("\\w{8}"));
			add("propertyB", random(1000L, 2000L));
			add("immutableInner", one(ImmutableInner.class, "immutable"));
		}})
		.addTemplate("threeParameterConstructor", new Rule(){{
			add("propertyB", random(1000L, 2000L));
			add("propertyC", regex("\\w{8}"));
			add("immutableInner", one(ImmutableInner.class, "immutable"));
			add("address", one(Address.class, "valid"));
		}})
		.addTemplate("fullConstructor", new Rule(){{
			add("propertyA", regex("\\w{8}"));
			add("propertyB", random(1000L, 2000L));
			add("propertyC", "${propertyA} based");
			add("date", instant("now"));
			add("address", one(Address.class, "valid"));
		}});
	
		Fixture.of(ImmutableInner.class).addTemplate("immutable", new Rule(){{ 
			add("propertyD", regex("\\w{8}"));
		}});
	
		Fixture.of(Address.class).addTemplate("valid", new Rule(){{
			add("id", random(Long.class, range(1L, 100L)));
			add("street", random("Paulista Avenue", "Ibirapuera Avenue"));
			add("city", "São Paulo");
			add("state", "${city}");
			add("country", "Brazil");
			add("zipCode", random("06608000", "17720000"));
		}});
	
		Fixture.of(Route.class)
	    	.addTemplate("valid", new Rule(){{
	    		add("id", one(RouteId.class, "valid"));
	    		add("cities", has(2).of(City.class, "valid"));
	    	}})
	    	.addTemplate("chainedId", new Rule(){{
	    		add("id.value", 2L);
	    		add("id.seq", 200L);
	    		add("cities", has(2).of(City.class, "valid"));
	    }});
    
	    Fixture.of(RouteId.class).addTemplate("valid", new Rule(){{
	    	add("value", 1L);
	    	add("seq", 100L);
	    }});
    
	    Fixture.of(RoutePlanner.class).addTemplate("chainedRoutePlanner", new Rule(){{
	        add("route.id.value", random(3L, 4L));
	        add("route.id.seq", random(300L, 400L));
	        add("route.cities", has(2).of(City.class, "valid"));
	    }});
	    
	    Fixture.of(City.class).addTemplate("valid", new Rule(){{
	        add("name", regex("\\w{8}"));
	    }});
	    
	    Fixture.of(Attribute.class).addTemplate("valid", new Rule(){{ 
	    	add("value", regex("\\w{8}"));
	    }});
	    
	    Fixture.of(Child.class).addTemplate("valid", new Rule(){{
	    	add("childAttribute", regex("\\w{16}"));
	    	add("parentAttribute", one(Attribute.class, "valid"));
	    }});
	    
	    Fixture.of(Child.class).addTemplate("chained", new Rule(){{
	        add("childAttribute", regex("\\w{16}"));
	        add("parentAttribute.value", regex("\\w{8}"));
	    }});        
	}
}
