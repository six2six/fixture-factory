package br.com.six2six.template;

import java.util.LinkedHashSet;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import br.com.six2six.fixturefactory.model.Attribute;
import br.com.six2six.fixturefactory.model.SetHolder;

public class SetHolderTemplate implements TemplateLoader {

    @Override
    public void load() {
        Fixture.of(SetHolder.class).addTemplate("has-n", new Rule() {{
            add("attributes", has(3).of(Attribute.class, "simple"));
        }});
    }
}
