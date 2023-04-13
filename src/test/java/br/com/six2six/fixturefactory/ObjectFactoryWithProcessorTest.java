package br.com.six2six.fixturefactory;

import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import br.com.six2six.fixturefactory.model.*;
import br.com.six2six.fixturefactory.processor.HibernateProcessor;
import br.com.six2six.fixturefactory.processor.Processor;
import org.hibernate.Session;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;

public class ObjectFactoryWithProcessorTest {

	private Session session;
	private Processor processor;

	@BeforeClass
	public static void setUpClass() {
		FixtureFactoryLoader.loadTemplates("br.com.six2six.template");
	}

	@Before
	public void setUp() {
		session = mock(Session.class);
		processor = new HibernateProcessor(session);
	}

	@Test
	public void shouldSavePersistentFixture() {
	    Fixture.from(City.class).uses(processor).gimme("valid");

		verify(session).save(isA(City.class));
	}

	@Test
	public void shouldSavePersistentFixtureAndHisRelations() {
	    Fixture.from(Client.class).uses(processor).gimme("valid");

		verify(session).save(isA(Client.class));
		verify(session).save(isA(Address.class));
	}

	@Test
	public void shouldSavePersistentFixtureAndRelationsOfHisRelations() {
	    Fixture.from(Client.class).uses(processor).gimme("valid");

		verify(session).save(isA(Client.class));
		verify(session).save(isA(Address.class));
		verify(session).save(isA(City.class));
	}

	@Test
	public void shouldSavePersistentFixtureCollections() {
	    Fixture.from(City.class).uses(processor).gimme(2, "valid");

		verify(session, times(2)).save(isA(City.class));
	}

	@Test
	public void shouldSavePersistentFixtureAnsHisRelationsCollections() {
	    Fixture.from(Order.class).uses(processor).gimme("valid");

		verify(session, times(3)).save(isA(Item.class));
		verify(session).save(isA(Order.class));
	}

	@Test
	public void shouldSavePersistentFixtureAndHisRelationsCollectionsOfHisRelations() {
	    Fixture.from(Client.class).uses(processor).gimme("valid");

		verify(session).save(isA(Client.class));
		verify(session).save(isA(Address.class));
		verify(session).save(isA(City.class));
		verify(session, times(2)).save(isA(Neighborhood.class));
	}

	@Test
	public void shouldSavePersistentFixtureAndHisConstructorParameterRelation() {
	    Fixture.from(Child.class).uses(processor).gimme("valid");

		verify(session).save(isA(Child.class));
		verify(session).save(isA(Attribute.class));
	}

	@Test
	public void shouldSavePersistentFixtureAndHisChainedConstructorParameterRelation() {
	    Fixture.from(Child.class).uses(processor).gimme("chained");

		verify(session).save(isA(Child.class));
		verify(session).save(isA(Attribute.class));
	}
}