package br.com.six2six.fixturefactory;

import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import br.com.six2six.fixturefactory.model.proto.SimplePropertyProto;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class FixtureSimplePropertyProtoTest {

    @BeforeClass
    public static void setUp() {
        FixtureFactoryLoader.loadTemplates("br.com.six2six.template");
    }
    
    @Test
    public void shouldGenerateProtoFixture() {
        SimplePropertyProto.Property proto = Fixture.from(SimplePropertyProto.Property.class).gimme("valid");

        assertNotNull(proto);
        assertTrue(proto.getId() > 0);
        assertTrue(proto.getBedroomsQuantity() > 0);
        assertNotNull(proto.getType());
        assertNotNull(proto.getAddress());
        assertNotNull(proto.getAddress().getStreet());
        assertNotNull(proto.getAddress().getStreetNumber());
        assertNotNull(proto.getAddress().getGeoLocation());
        assertTrue(proto.getAddress().getGeoLocation().getLatitude() > 0);
        assertTrue(proto.getAddress().getGeoLocation().getLongitude() > 0);
    }
    
}