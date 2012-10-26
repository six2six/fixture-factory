package br.com.fixturefactory;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;

import br.com.fixturefactory.model.Address;
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
	}
	
	@Test
	public void createImmutableObjectWithPartialConstructorStringAndLong() {
		Immutable result = Fixture.from(Immutable.class).gimme("twoParameterConstructor");
		
		assertNotNull(result.getPropertyA());
		assertNotNull(result.getPropertyB());
		assertEquals("default", result.getPropertyC());
		assertNotNull(result.getImmutableInner().getPropertyD());
		assertNull(result.getDate());
		assertNull(result.getAddress());
	}

	@Test
	public void createImmutableObjectWithPartialConstructorLongAndString() {
		Immutable result = Fixture.from(Immutable.class).gimme("threeParameterConstructor");
		
		assertEquals("default", result.getPropertyA());
		assertNotNull(result.getPropertyB());
		assertNotNull(result.getPropertyC());
		assertNotNull(result.getImmutableInner().getPropertyD());
		assertNull(result.getDate());
		assertNotNull(result.getAddress());
	}
	
	@Test
	public void createImmutableObjectWithFullConstructor() {
		Immutable result = Fixture.from(Immutable.class).gimme("fullConstructor");
		
		assertNotNull(result.getPropertyA());
		assertNotNull(result.getPropertyB());
		assertEquals(result.getPropertyA() + " based", result.getPropertyC());
		assertNotNull(result.getDate());
		assertNotNull(result.getAddress());
	}
}
