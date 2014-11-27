package br.com.six2six.fixturefactory;

import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import br.com.six2six.fixturefactory.model.*;

import org.junit.*;

import java.util.List;

public class FixtureProgrammerTest {

    @Before
    public void setUp() throws Exception {
        FixtureFactoryLoader.loadTemplates("br.com.six2six.template");
    }

    @Test
    public void testWithSuccessUsingDefaultRetry() throws Exception {
        List<Programmer> programmers = Fixture.from(Programmer.class).gimme(50, "valid");

        this.assertUnique(programmers);
    }

    private void assertUnique(List<Programmer> programmers) {
        for (Programmer programmer : programmers) {
            Skill skill1 = programmer.getSkills().get(0);
            Skill skill2 = programmer.getSkills().get(1);

            Assert.assertTrue(!skill1.equals(skill2));
        }
    }

    @Test(expected = RuntimeException.class)
    public void testWithErrorUsingDefaultRetry() throws Exception {
        Fixture.from(Programmer.class).gimme(1, "invalid");
    }

    @Test
    public void testWithSuccessUsingCustomRetry() throws Exception {
        List<Programmer> programmers = Fixture.from(Programmer.class).gimme(50, "valid_custom_retry");

        this.assertUnique(programmers);
    }

}
