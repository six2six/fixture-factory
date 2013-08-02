package br.com.six2six.fixturefactory;

import static junit.framework.Assert.assertNotNull;

import org.junit.BeforeClass;
import org.junit.Test;

import br.com.fixturefactory.loader.FixtureFactoryLoader;
import br.com.six2six.fixturefactory.model.User;

public class FixtureUserTest {

	@BeforeClass
	public static void setUp() {
		FixtureFactoryLoader.loadTemplates("br.com.six2six.template");
	}

	@Test
	public void fixtureAnyUser() {
		User user = Fixture.from(User.class).gimme("anyValidUser");
		assertNotNull("User should not be null", user);
	}

	@Test
	public void fixtureFemaleUser() {
		User user = Fixture.from(User.class).gimme("validFemaleUser");
		assertNotNull("User should not be null", user);
	}
	
}
