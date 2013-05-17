package br.com.six2six.fixturefactory;

import static junit.framework.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.model.XMLTransaction;

public class FixtureXMLTransactionTest {

	@Before
	public void setUp() {
		Fixture.of(XMLTransaction.class).addTemplate("validTransaction", new Rule(){{
			add("origin", "ORIGIN");
			add("date", instant("now"));
			add("hour", instant("now"));
		}});
	}

	@Test
	public void fixtureValidTransaction() {
		XMLTransaction transaction = Fixture.from(XMLTransaction.class).gimme("validTransaction");
		assertNotNull("User should not be null", transaction);
	}
	
}
