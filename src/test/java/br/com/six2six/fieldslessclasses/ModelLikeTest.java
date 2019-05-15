package br.com.six2six.fieldslessclasses;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ModelLikeTest {

    @BeforeClass
    public static void setup() {
        FixtureFactoryLoader.loadTemplates("br.com.six2six.fieldslessclasses");
    }

    @Test
    public void testModelLikeLoader() {
        ModelLike modelLike = Fixture.from(ModelLike.class).gimme("test");
        Assert.assertNotNull(modelLike);
        Assert.assertEquals(modelLike.getCode(),"123");
        Assert.assertEquals(modelLike.getNumber().intValue(),1);
        Assert.assertEquals(modelLike.getaBoolean(),true);
    }
}

