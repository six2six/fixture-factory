package br.com.six2six.fixturefactory;

import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import br.com.six2six.fixturefactory.model.User;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class GenericsTypeInferTest {

    @BeforeClass
    public static void setUp() {
        FixtureFactoryLoader.loadTemplates("br.com.six2six.template");
    }

    /**
     * This will also be useful to work with java 11 var statement
     */
    @Test
    public void shouldInferListType() {
        validate(Arrays.asList(Fixture.from(User.class).gimme("validFemaleUser")));
    }

    private void validate(List<User> users) {}
}
