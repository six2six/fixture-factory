package br.com.six2six.fixturefactory;

import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import br.com.six2six.fixturefactory.model.LambdaPlane;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class FixtureLambdaPlaneTest {

    @BeforeClass
    public static void beforeClass() {
        FixtureFactoryLoader.loadTemplates("br.com.six2six.template");
    }

    @Test
    public void shouldCreateInstanceInClassUsingLambda() throws Exception {
        LambdaPlane validPlane = Fixture.from(LambdaPlane.class).gimme("valid");

        assertThat(validPlane, is( notNullValue()) );
        assertThat(validPlane.getFlightCrew().size(), is(equalTo( 3 )));
    }
}
