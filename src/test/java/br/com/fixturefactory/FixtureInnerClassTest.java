package br.com.fixturefactory;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;

import org.junit.BeforeClass;
import org.junit.Test;

import br.com.fixturefactory.loader.FixtureFactoryLoader;
import br.com.fixturefactory.model.Owner;

public class FixtureInnerClassTest {

	@BeforeClass
	public static void setUp() {
		FixtureFactoryLoader.loadTemplates("br.com.fixturefactory.template");
	}
	
	@Test
	public void shouldCreateObjectWithInnerClass() {
		Owner owner = Fixture.from(Owner.class).gimme("valid");
		
		assertNotNull("owner should not be null", owner);
		assertNotNull("owner.inner should not be null", owner.getInner());
		
		assertSame("owner.inner.owner should be same of owner", owner.getInner().getOwner(), owner);
		assertEquals("222", owner.getInner().getId());
	}
	
	@Test
    public void shouldCreateObjectWithInnerClassChainedProperty() {
        Owner owner = Fixture.from(Owner.class).gimme("chained");
        
        assertNotNull("owner should not be null", owner);
        assertNotNull("owner.inner should not be null", owner.getInner());
        
        assertSame("owner.inner.owner should be same of owner", owner.getInner().getOwner(), owner);
        assertEquals("333", owner.getInner().getId());
    }
}
