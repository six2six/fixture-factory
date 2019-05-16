package br.com.six2six.fixturefactory;

import static org.junit.Assert.assertNotNull;

import org.junit.BeforeClass;
import org.junit.Test;

import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import br.com.six2six.fixturefactory.model.People;

public class FixturePeopleTest {

	@BeforeClass
	public static void setUp() {
		FixtureFactoryLoader.loadTemplates("br.com.six2six.template");
	}
	
	@Test
	public void fixtureClient() {
		People people = Fixture.from(People.class).gimme("random");
		assertNotNull("People should not be null", people);
		assertNotNull("Name should not be null", people.getName());
		assertNotNull("Age should not be null", people.getAge());
		assertNotNull("Doc should not be null", people.getDoc());
		assertNotNull("Borth should not be null", people.getBirth());
	}

}