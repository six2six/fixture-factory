package br.com.six2six.fixturefactory;

import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import br.com.six2six.fixturefactory.model.Programmer;

import br.com.six2six.fixturefactory.model.Skill;
import org.junit.*;

import java.util.List;

public class FixtureProgrammerTest {

    @Before
    public void setUp() throws Exception {
        FixtureFactoryLoader.loadTemplates("br.com.six2six.template");
    }

    @Test
    public void test() throws Exception {
        List<Programmer> programmers = Fixture.from(Programmer.class).gimme(5, "valid");

        for (Programmer programmer : programmers) {
            Skill skill1 = programmer.getSkills().get(0);
            Skill skill2 = programmer.getSkills().get(1);

            Assert.assertTrue(!skill1.equals(skill2));
        }
    }
}
