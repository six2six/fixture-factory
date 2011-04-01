package br.com.fixturefactory;

import java.util.Calendar;
import java.util.List;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import br.com.fixturefactory.model.Client;

public class FixtureTest {

	@Before
	public void setUp() {
		Fixture.of(Client.class).addTemplate("valid", new Rule(){{
			add("id", random(Long.class, range(1L, 200L)));
			add("name", random(new Object[]{"Anderson Parra", "Arthur Hirata"}));
			add("nickname", random(new Object[]{"nerd", "geek"}));
			add("email", "${nickname}@gmail.com");
			add("birthday", Calendar.getInstance());
		}});
	}
	
	@Test
	public void fixtureClient() {
		Client client = Fixture.of(Client.class).gimme("valid");
		Assert.assertNotNull("Client should not be null", client);
	}
	
	@Test
	public void fixtureClientList() {
		List<Client> clients = Fixture.of(Client.class).gimme(5, "valid");
		
		Assert.assertNotNull("Client list should not be null", clients);
		Assert.assertFalse("Client list should not be empty", clients.isEmpty());
		Assert.assertEquals("Client list size should be 5", clients.size(), 5);
		
		for (Client client : clients) {
			Assert.assertNotNull("Client should not be null", client);
		}
	}
	
}
