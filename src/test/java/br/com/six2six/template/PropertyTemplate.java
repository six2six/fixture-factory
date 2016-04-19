package br.com.six2six.template;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import br.com.six2six.fixturefactory.model.proto.SimplePropertyProto;

public class PropertyTemplate implements TemplateLoader {

    @Override
    public void load() {
        Fixture.of(SimplePropertyProto.Property.class).addTemplate("valid", new Rule() {{
            add("id", sequence(Integer.class));
            add("bedroomsQuantity", random(Integer.class, range(1, 10)));
            add("address", one(SimplePropertyProto.Address.class, "valid"));
            add("type", random(SimplePropertyProto.Type.class));

        }});

        Fixture.of(SimplePropertyProto.Address.class).addTemplate("valid", new Rule() {{
            add("street", "Rua Bela Cintra");
            add("streetNumber", "539");
            add("geoLocation", one(SimplePropertyProto.GeoLocation.class, "valid"));
        }});

        Fixture.of(SimplePropertyProto.GeoLocation.class).addTemplate("valid", new Rule() {{
            add("latitude", 10.0);
            add("longitude", 10.0);
        }});
    }

}