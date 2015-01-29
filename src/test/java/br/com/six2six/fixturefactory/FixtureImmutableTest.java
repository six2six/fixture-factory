package br.com.six2six.fixturefactory;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.matchers.JUnitMatchers.either;

import org.junit.BeforeClass;
import org.junit.Test;

import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import br.com.six2six.fixturefactory.model.Child;
import br.com.six2six.fixturefactory.model.Immutable;
import br.com.six2six.fixturefactory.model.Route;
import br.com.six2six.fixturefactory.model.RoutePlanner;

public class FixtureImmutableTest {

	@BeforeClass
	public static void setUp() {
		FixtureFactoryLoader.loadTemplates("br.com.six2six.template");
	}
	
	@Test
	public void shouldCreateImmutableObjectUsingCorrectPartialConstructor() {
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

    @Test
    public void shouldWorkWithInheritance() {
        Child child = Fixture.from(Child.class).gimme("valid");
    	assertThat(child.getParentAttribute().getValue().length(), is(8));
    	assertThat(child.getChildAttribute().length(), is(16));
    }
    
    @Test
    public void shouldWorkWhenChainingInheritedProperty() {
        Child child = Fixture.from(Child.class).gimme("chained");
        assertThat(child.getParentAttribute().getValue().length(), is(8));
        assertThat(child.getChildAttribute().length(), is(16));
    }
    
    @Test
    public void shouldOverrideNestedObjectAttribute() {
    	Immutable result = Fixture.from(Immutable.class).gimme("fullConstructor", new Rule() {{
    		add("address.street", "Rua do Nykolas");
    	}});
    	
    	assertThat(result.getAddress().getStreet(), equalTo("Rua do Nykolas"));
    }
    
}
