package br.com.fixturefactory;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.matchers.JUnitMatchers.either;

import org.junit.Before;
import org.junit.Test;

import br.com.fixturefactory.model.Address;
import br.com.fixturefactory.model.City;
import br.com.fixturefactory.model.Immutable;
import br.com.fixturefactory.model.Immutable.ImmutableInner;
import br.com.fixturefactory.model.Route;
import br.com.fixturefactory.model.RouteId;

public class FixtureImmutableTest {

	@Before
	public void setUp() {
		Fixture.of(Immutable.class)
			.addTemplate("twoParameterConstructor", new Rule() {{
				add("propertyA", regex("\\w{8}"));
				add("propertyB", random(1000L, 2000L));
				add("immutableInner", fixture(ImmutableInner.class, "immutable"));
			}})
			.addTemplate("threeParameterConstructor", new Rule() {{
				add("propertyB", random(1000L, 2000L));
				add("propertyC", regex("\\w{8}"));
				add("immutableInner", fixture(ImmutableInner.class, "immutable"));
				add("address", fixture(Address.class, "valid"));
			}})
			.addTemplate("fullConstructor", new Rule() {{
				add("propertyA", regex("\\w{8}"));
				add("propertyB", random(1000L, 2000L));
				add("propertyC", "${propertyA} based");
				add("date", instant("now"));
				add("address", fixture(Address.class, "valid"));
			}});
		Fixture.of(ImmutableInner.class)
			.addTemplate("immutable", new Rule() {{ 
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
        	.addTemplate("valid", new Rule() {{
        		add("id", one(RouteId.class, "valid"));
        		add("cities", has(2).of(City.class, "valid"));
        	}})
        	.addTemplate("chainedId", new Rule() {{
        		add("id.value", 2L);
        		add("id.seq", 200L);
        		add("cities", has(2).of(City.class, "valid"));
        }});
        
        Fixture.of(RouteId.class).addTemplate("valid", new Rule() {{
        	add("value", 1L);
        	add("seq", 100L);
        }});
        
        Fixture.of(RoutePlanner.class).addTemplate("chainedRoutePlanner", new Rule() {{
            add("route.id.value", random(3L, 4L));
            add("route.id.seq", random(300L, 400L));
            add("route.cities", has(2).of(City.class, "valid"));
        }});
        
        Fixture.of(City.class).addTemplate("valid", new Rule() {{
            add("name", regex("\\w{8}"));
        }});
	}
	
	@Test
	public void shouldCreateImmutableObjectUsingPartialConstructor() {
		Immutable result = Fixture.from(Immutable.class).gimme("twoParameterConstructor");
		
		assertNotNull(result.getPropertyA());
		assertNotNull(result.getPropertyB());
		assertEquals("default", result.getPropertyC());
		assertNotNull(result.getImmutableInner().getPropertyD());
		assertNull(result.getDate());
		assertNull(result.getAddress());
	}

	@Test
	public void shouldCreateImmutableObjectUsingAnotherPartialConstructor() {
		Immutable result = Fixture.from(Immutable.class).gimme("threeParameterConstructor");
		
		assertEquals("default", result.getPropertyA());
		assertNotNull(result.getPropertyB());
		assertNotNull(result.getPropertyC());
		assertNotNull(result.getImmutableInner().getPropertyD());
		assertNull(result.getDate());
		assertNotNull(result.getAddress());
	}
	
	@Test
	public void shouldCreateImmutableObjectUsingFullConstructor() {
		Immutable result = Fixture.from(Immutable.class).gimme("fullConstructor");
		
		assertNotNull(result.getPropertyA());
		assertNotNull(result.getPropertyB());
		assertEquals(result.getPropertyA() + " based", result.getPropertyC());
		assertNotNull(result.getDate());
		assertNotNull(result.getAddress());
		assertEquals(result.getAddress().getCity(), result.getAddress().getState());
	}
	
	@Test
	public void shouldWorkWhenReceivingRelationsInTheConstructor() {
		Route route = Fixture.from(Route.class).gimme("valid");
		assertEquals(Long.valueOf(1L), route.getId().getValue());
		assertEquals(Long.valueOf(100L), route.getId().getSeq());
		assertNotNull(route.getCities().get(0).getName());
	}
	
    @Test 
    public void shouldWorkWhenChainingProperties() { 
        Route route = Fixture.from(Route.class).gimme("chainedId"); 
        assertEquals(Long.valueOf(2L), route.getId().getValue());
        assertEquals(Long.valueOf(200L), route.getId().getSeq());
        assertNotNull(route.getCities().get(0).getName()); 
    }
    
    @Test
    public void shouldWorkWhenChainingPropertiesUsingRelations() {
        RoutePlanner routePlanner = Fixture.from(RoutePlanner.class).gimme("chainedRoutePlanner");
        assertThat(routePlanner.getRoute().getId().getValue(), is(either(equalTo(3L)).or(equalTo(4L))));
        assertThat(routePlanner.getRoute().getId().getSeq(), is(either(equalTo(300L)).or(equalTo(400L))));
        assertNotNull(routePlanner.getRoute().getCities().get(0).getName());
    }
}
