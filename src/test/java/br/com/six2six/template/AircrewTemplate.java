package br.com.six2six.template;


import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import br.com.six2six.fixturefactory.model.Aircrew;

public class AircrewTemplate implements TemplateLoader {

    @Override
    public void load() {
        Fixture.of(Aircrew.class).addTemplate("random", new Rule(){{
            add("name", firstName());
        }});
    }
}
