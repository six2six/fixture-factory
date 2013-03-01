package br.com.fixturefactory;

import static junit.framework.Assert.assertNotNull;

import org.junit.BeforeClass;
import org.junit.Test;

import br.com.fixturefactory.loader.FixtureFactoryLoader;
import br.com.fixturefactory.model.XMLTransaction;

public class FixtureXMLTransactionTest {

	@BeforeClass
	public static void setUp() {
		FixtureFactoryLoader.loadTemplates("br.com.fixturefactory.template");
	}

	@Test
	public void fixtureValidTransaction() {
		XMLTransaction transaction = Fixture.from(XMLTransaction.class).gimme("validTransaction");
		assertNotNull("User should not be null", transaction);
	}
	
}
