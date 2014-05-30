package br.com.six2six.fixturefactory;

import static junit.framework.Assert.assertEquals;

import org.junit.BeforeClass;
import org.junit.Test;

import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import br.com.six2six.fixturefactory.model.Route;

public class RouteFixtureTest {

	@BeforeClass
	public static void setUp() {
		FixtureFactoryLoader.loadTemplates("br.com.six2six.template");
	}
	
	@Test
	public void shouldBuildRouteWithTwoDifferentCitiesTemplates() {
		Route route = Fixture.from(Route.class).gimme("routeWithDifferentCities");
		assertEquals("São Paulo", route.getCities().get(0).getName());
		assertEquals("Rio de Janeiro", route.getCities().get(1).getName());
	}
	
}
