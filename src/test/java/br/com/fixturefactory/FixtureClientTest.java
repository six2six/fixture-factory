package br.com.fixturefactory;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNotNull;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.mdimension.jchronic.Options;
import com.mdimension.jchronic.utils.Time;

import br.com.fixturefactory.model.Address;
import br.com.fixturefactory.model.Client;

public class FixtureClientTest {

	@Before
	public void setUp() {
		Fixture.of(Client.class).addTemplate("valid", new Rule(){{
			add("id", random(Long.class, range(1L, 200L)));
			add("name", random("Anderson Parra", "Arthur Hirata"));
			add("nickname", random("nerd", "geek"));
			add("email", "${nickname}@gmail.com");
			add("birthday", instant("18 years ago"));
			add("address", fixture(Address.class, "valid"));
		}});
		
		Fixture.of(Address.class).addTemplate("valid", new Rule(){{
			add("id", random(Long.class, range(1L, 100L)));
			add("street", random("Paulista Avenue", "Ibirapuera Avenue"));
			add("city", "São Paulo");
			add("state", "${city}");
			add("country", "Brazil");
			add("zipCode", random("06608000", "17720000"));
		}});
	}
	
	@Test
	public void fixtureClient() {
		Client client = Fixture.of(Client.class).gimme("valid");
		assertNotNull("Client should not be null", client);
		assertNotNull("Address should not be null", client.getAddress());
		assertEquals("client birthday should be 18 years ago", client.getBirthday().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.YEAR) - 18);
	}
	
	@Test
	public void fixtureClientList() {
		List<Client> clients = Fixture.of(Client.class).gimme(5, "valid");
		
		assertNotNull("Client list should not be null", clients);
		assertFalse("Client list should not be empty", clients.isEmpty());
		assertEquals("Client list size should be 5", clients.size(), 5);
		
		for (Client client : clients) {
			assertNotNull("Client should not be null", client);
			assertNotNull("Address should not be null", client.getAddress());
		}
	}
	
}
