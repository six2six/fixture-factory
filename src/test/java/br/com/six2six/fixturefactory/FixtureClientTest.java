package br.com.six2six.fixturefactory;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import java.util.Calendar;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import br.com.six2six.fixturefactory.model.Address;
import br.com.six2six.fixturefactory.model.Client;

public class FixtureClientTest {

	@BeforeClass
	public static void setUp() {
		FixtureFactoryLoader.loadTemplates("br.com.six2six.template");
	}

	@Test
	public void fixtureClient() {
		Client client = Fixture.from(Client.class).gimme("valid");
		assertNotNull("Client should not be null", client);
		assertNotNull("Address should not be null", client.getAddress());
		assertEquals("client birthday should be 18 years ago", client.getBirthday().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.YEAR) - 18);
	}

	@Test
	public void fixtureAddress() {
		Address address = Fixture.from(Address.class).gimme("valid-augusta");
		assertNotNull("Address should not be null", address.getId());
		assertEquals("Augusta Street", address.getStreet());
	}

	@Test
	public void fixtureClientList() {
		List<Client> clients = Fixture.from(Client.class).gimme(5, "valid");

		assertNotNull("Client list should not be null", clients);
		assertFalse("Client list should not be empty", clients.isEmpty());
		assertEquals("Client list size should be 5", clients.size(), 5);

		for (Client client : clients) {
			assertNotNull("Client should not be null", client);
			assertNotNull("Address should not be null", client.getAddress());
		}
	}

	@Test
	public void shouldCreateClientWithAddressEqualToNull() {
		Client client = Fixture.from(Client.class).gimme("valid-noaddress");

		assertNotNull(client.getId());
		assertNull(client.getAddress());
	}

	@Test
	public void shouldOverridedProperty() throws Exception {
		Client client = Fixture.from(Client.class).gimme("valid", new Rule() {{
			add("name", "Fixture Factory");
		}});

		assertThat(client.getName(), equalTo("Fixture Factory"));
	}

	@Test
	public void shouldOverridedPropertyForAllInstances() throws Exception {
		List<Client> clients = Fixture.from(Client.class).gimme(3, "valid", new Rule() {{
			add("name", "Fixture Factory");
		}});

		for (Client client : clients) {
			assertThat(client.getName(), equalTo("Fixture Factory"));
		}
	}

}