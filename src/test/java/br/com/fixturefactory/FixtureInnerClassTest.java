package br.com.fixturefactory;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import br.com.fixturefactory.model.Owner;

public class FixtureInnerClassTest {

	@Before
	public void setUp() {
		Fixture.of(Owner.class).addTemplate("valid", new Rule(){{
			add("inner", fixture(Owner.Inner.class, "valid"));
		}});
		
		Fixture.of(Owner.Inner.class).addTemplate("valid", new Rule(){{
			add("id", "222");
		}});
	}
	
	@Test
	public void innerClassIntanceOnOwnerProperty() {
		Owner owner = Fixture.from(Owner.class).gimme("valid");
		
		assertNotNull("owner should not be null", owner);
		assertNotNull("owner.inner should not be null", owner.getInner());
		
		assertTrue("owner.inner.owner should be same of owner", owner.getInner().getOwner() == owner);

	}
}
