package br.com.fixturefactory;

import static junit.framework.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

import br.com.bfgex.Gender;
import br.com.fixturefactory.model.User;

public class FixtureUserTest {

	@Before
	public void setUp() {
		Fixture.of(User.class).addTemplate("anyValidUser", new Rule(){{
			add("name", name());
			add("login", random("login1", "login2"));
			add("password", "madona");
			add("gender", random(Gender.class));
			add("email", "${login}@gmail.com");
		}}
		).addTemplate("validFemaleUser", new Rule(){{
			add("name", name(Gender.FEMALE));
			add("login", "${name}");
			add("password", name(Gender.MALE));
			add("gender", Gender.FEMALE);
			add("email", "duck@gmail.com");
		}});
	}

	@Test
	public void fixtureAnyUser() {
		User user = Fixture.of(User.class).gimme("anyValidUser");
		assertNotNull("User should not be null", user);
	}

	@Test
	public void fixtureFemaleUser() {
		User user = Fixture.of(User.class).gimme("validFemaleUser");
		assertNotNull("User should not be null", user);
	}
	
}
