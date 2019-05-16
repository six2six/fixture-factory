package br.com.six2six.template;

import java.text.SimpleDateFormat;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import br.com.six2six.fixturefactory.model.People;

public class PeopleTemplate implements TemplateLoader {
    @Override
    public void load() {
        Fixture.of(People.class).addTemplate("random", new Rule(){{
            add("age", uniqueRandom(1, 20, Integer.class));
            add("doc", uniqueRandom(1, 20, Long.class));
            add("name", firstName());
            add("birth", randomDate("1996-03-30", "2018-11-03", new SimpleDateFormat("yyyy-MM-dd")));
        }});
    }
}