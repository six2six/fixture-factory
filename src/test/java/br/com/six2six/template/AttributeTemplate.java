package br.com.six2six.template;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import br.com.six2six.fixturefactory.model.Attribute;

public class AttributeTemplate implements TemplateLoader {

    @Override
    public void load() {
        Fixture.of(Attribute.class).addTemplate("simple", new Rule(){{
            add("value", regex("\\w{5}"));
        }});
    }
}
