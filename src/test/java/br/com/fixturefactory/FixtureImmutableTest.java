package br.com.fixturefactory;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

import br.com.fixturefactory.model.Immutable;
import br.com.fixturefactory.model.Immutable.ImmutableInner;

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
				add("propertyA", regex("\\w{8}"));
				add("propertyB", random(1000L, 2000L));
				add("propertyC", "${propertyA} based");
				add("date", instant("now"));
			}});
		Fixture.of(ImmutableInner.class)
			.addTemplate("immutable", new Rule() {{ 
				add("propertyD", regex("\\w{8}"));
			}});
	}
	
	@Test
	public void createImmutableObjectWithPartialConstructor() {
		Immutable result = Fixture.from(Immutable.class).gimme("twoParameterConstructor");
		
		assertNotNull(result.getPropertyA());
		assertNotNull(result.getPropertyB());
		assertEquals("default", result.getPropertyC());
		assertNotNull(result.getImmutableInner().getPropertyD());
	}
	
	@Test
	public void createImmutableObjectWithFullConstructor() {
		Immutable result = Fixture.from(Immutable.class).gimme("threeParameterConstructor");
		
		assertNotNull(result.getPropertyA());
		assertNotNull(result.getPropertyB());
		assertEquals(result.getPropertyA() + " based", result.getPropertyC());
	}
}
