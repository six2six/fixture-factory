package br.com.six2six.fixturefactory;

import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.hibernate.Session;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import br.com.six2six.fixturefactory.context.HibernateProcessor;
import br.com.six2six.fixturefactory.context.Processor;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import br.com.six2six.fixturefactory.model.Address;
import br.com.six2six.fixturefactory.model.Attribute;
import br.com.six2six.fixturefactory.model.Child;
import br.com.six2six.fixturefactory.model.City;
import br.com.six2six.fixturefactory.model.Client;
import br.com.six2six.fixturefactory.model.Item;
import br.com.six2six.fixturefactory.model.Neighborhood;
import br.com.six2six.fixturefactory.model.Order;

public class ObjectFactoryProcessorTest {

	private ObjectFactoryProcessor factory;
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
	    factory = new ObjectFactoryProcessor(Fixture.of(City.class), processor);
		
	    factory.gimme("valid");
	    
		verify(session).save(isA(City.class));
	}
	
	@Test
	public void shouldSavePersistentFixtureAndHisRelations() {
		factory = new ObjectFactoryProcessor(Fixture.of(Client.class), processor);
		
		factory.gimme("valid");
		
		verify(session).save(isA(Client.class));
		verify(session).save(isA(Address.class));
	}
	
	@Test
	public void shouldSavePersistentFixtureAndRelationsOfHisRelations() {
		factory = new ObjectFactoryProcessor(Fixture.of(Client.class), processor);
		
		factory.gimme("valid");
		
		verify(session).save(isA(Client.class));
		verify(session).save(isA(Address.class));
		verify(session).save(isA(City.class));
	}
	
	@Test
	public void shouldSavePersistentFixtureCollections() {
		factory = new ObjectFactoryProcessor(Fixture.of(City.class), processor);
		
		factory.gimme(2, "valid");
		
		verify(session, times(2)).save(isA(City.class));
	}
	
	@Test
	public void shouldSavePersistentFixtureAnsHisRelationsCollections() {
		factory = new ObjectFactoryProcessor(Fixture.of(Order.class), processor);
		
		factory.gimme("valid");
		
		verify(session, times(3)).save(isA(Item.class));
		verify(session).save(isA(Order.class));
	}
	
	@Test
	public void shouldSavePersistentFixtureAndHisRelationsCollectionsOfHisRelations() {
		factory = new ObjectFactoryProcessor(Fixture.of(Client.class), processor);
		
		factory.gimme("valid");
		
		verify(session).save(isA(Client.class));
		verify(session).save(isA(Address.class));
		verify(session).save(isA(City.class));
		verify(session, times(2)).save(isA(Neighborhood.class));
	}
	
	@Test
	public void shouldSavePersistentFixtureAndHisConstructorParameterRelation() {
		factory = new ObjectFactoryProcessor(Fixture.of(Child.class), processor);
		
		factory.gimme("valid");
		
		verify(session).save(isA(Child.class));
		verify(session).save(isA(Attribute.class));
	}
	
}
