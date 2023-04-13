package br.com.six2six.fixturefactory;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import br.com.six2six.fixturefactory.model.User;

public class ObjectFactoryTest {

	private ObjectFactory objectFactory;

	@Before
	public void setUp() {
		TemplateHolder templateHolder = mockTemplateHolder();
		objectFactory = new ObjectFactory(templateHolder);
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldThrowExceptionForGimmeWithQuantityAndWrongNumberOfTemplates() {
		objectFactory.gimme(3, Arrays.asList("template1", "template2"));
	}

	//hack to workaround Mockito error when trying to return a Class<?>
	private TemplateHolder mockTemplateHolder() {
		TemplateHolder templateHolder = mock(TemplateHolder.class);
		final Class<?> clazz = User.class;
		when(templateHolder.getClazz()).thenAnswer(new Answer<Class<?>>() {
			@Override
			public Class<?> answer(InvocationOnMock invocation) throws Throwable {
				return clazz;
			}
		});

		return templateHolder;
	}

}