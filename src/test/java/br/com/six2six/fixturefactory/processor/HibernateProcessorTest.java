package br.com.six2six.fixturefactory.processor;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import javax.persistence.Embeddable;

import org.hibernate.Session;
import org.junit.Test;

public class HibernateProcessorTest {

	@Test
	public void shouldNotSaveEmbeddableClasses() {
		Session session = mock(Session.class);
		Processor processor = new HibernateProcessor(session);
		EmbeddableClass embeddableClass = new EmbeddableClass();
		processor.execute(embeddableClass);
		
		verify(session, never()).save(embeddableClass);
	}
	
	@Embeddable
	private class EmbeddableClass {
		
	}
	
}
