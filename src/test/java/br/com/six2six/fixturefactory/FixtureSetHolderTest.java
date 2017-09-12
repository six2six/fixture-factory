package br.com.six2six.fixturefactory;

import static org.junit.Assert.assertEquals;

import org.junit.BeforeClass;
import org.junit.Test;

import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import br.com.six2six.fixturefactory.model.SetHolder;

public class FixtureSetHolderTest {

    @BeforeClass
    public static void setUp() {
        FixtureFactoryLoader.loadTemplates("br.com.six2six.template");
    }

    @Test
    public void fixtureSortedSet() {
        SetHolder holder = Fixture.from(SetHolder.class).gimme("has-n");
        assertEquals("Expected three items", 3, holder.getAttributes().size());
    }
}
