package br.com.fixturefactory;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

import br.com.fixturefactory.model.Address;
import br.com.fixturefactory.model.Client;
import br.com.fixturefactory.model.Employee;
import br.com.fixturefactory.model.Position;

public class FixtureEnumTest {
	
	@Before
	public void setUp(){
		
		Fixture.of(Employee.class).addTemplate("valid", new Rule(){{
			
			add("name", "teste");
			add("positions", has(10).of(Position.class));
			add("clients", has(5).of(Client.class, "valid"));
			
		}});
		
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
	public void testEnumList(){
		
		Employee employee = Fixture.from(Employee.class).gimme("valid");
		assertNotNull("Employee should not be null" ,employee);
		assertEquals(10, employee.getPositions().size());
		assertEquals(5, employee.getClients().size());
		
	}

}
