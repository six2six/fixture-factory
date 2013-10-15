package br.com.six2six.fixturefactory;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.BeforeClass;
import org.junit.Test;

import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import br.com.six2six.fixturefactory.model.NullProperty;

public class FixtureNullPropertyTest {
	
	@BeforeClass
	public static void setUp() {
		FixtureFactoryLoader.loadTemplates("br.com.six2six.template");
	}
	
	@Test
	public void shouldCreateAnObjectWithAllPropertiesFilled() {
		NullProperty result = Fixture.from(NullProperty.class).gimme("allNames");
		
		assertNotNull(result.getName());
		assertNotNull(result.getOtherName());
	}
	
	@Test
	public void shouldSetNullInAPropertyOfObjectFromInheritedTemplate() {
		NullProperty result = Fixture.from(NullProperty.class).gimme("onlyName");
		
		assertNotNull(result.getName());
		assertNull(result.getOtherName());
	}
}
