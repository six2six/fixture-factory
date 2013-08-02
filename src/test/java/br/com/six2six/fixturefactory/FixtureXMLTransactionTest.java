package br.com.six2six.fixturefactory;

import static junit.framework.Assert.assertNotNull;

import org.junit.BeforeClass;
import org.junit.Test;

import br.com.fixturefactory.loader.FixtureFactoryLoader;
import br.com.six2six.fixturefactory.model.XMLTransaction;

public class FixtureXMLTransactionTest {

	@BeforeClass
	public static void setUp() {
		FixtureFactoryLoader.loadTemplates("br.com.six2six.template");
	}

	@Test
	public void fixtureValidTransaction() {
		XMLTransaction transaction = Fixture.from(XMLTransaction.class).gimme("validTransaction");
		assertNotNull("User should not be null", transaction);
	}
	
}
