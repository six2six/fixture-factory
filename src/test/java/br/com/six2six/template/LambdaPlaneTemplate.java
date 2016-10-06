package br.com.six2six.template;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import br.com.six2six.fixturefactory.model.Aircrew;
import br.com.six2six.fixturefactory.model.LambdaPlane;

public class LambdaPlaneTemplate implements TemplateLoader {


    @Override
    public void load() {
        Fixture.of(LambdaPlane.class).addTemplate("valid", new Rule(){{
            add("tailNumber", "A6-EDY");
            add("flightCrew", has(3).of(Aircrew.class, "random"));
        }});
    }
}
