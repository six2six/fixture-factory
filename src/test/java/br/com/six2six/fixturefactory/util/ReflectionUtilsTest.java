package br.com.six2six.fixturefactory.util;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import br.com.six2six.fixturefactory.model.Immutable;
import br.com.six2six.fixturefactory.model.Owner;

public class ReflectionUtilsTest {

	@Test
	public void shouldFindDefaultConstructorForTopLevelClass() {
		assertThat(ReflectionUtils.hasDefaultConstructor(Owner.class), is(true));
	}
	
	@Test
	public void shouldFindDefaultConstructorForInnerClass() {
		assertThat(ReflectionUtils.hasDefaultConstructor(Owner.Inner.class), is(true));
	}
	
	@Test
	public void shouldntFindAnyDefaultConstructorForTopLevelClass() {
		assertThat(ReflectionUtils.hasDefaultConstructor(Immutable.class), is(false));
	}
	
	@Test
	public void shouldntFindDefaultConstructorForInnerClass() {
		assertThat(ReflectionUtils.hasDefaultConstructor(Immutable.ImmutableInner.class), is(false));
	}
	
}
