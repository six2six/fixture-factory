package br.com.six2six.fieldslessclasses;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;

public class ModelLikeTemplateLoader implements TemplateLoader {

    @Override
    public void load() {
        Fixture.of(ModelLike.class).addTemplate("test", new Rule() {{
            add("code","123");
            add("number",1);
            add("aBoolean",true);
        }});
    }
}
