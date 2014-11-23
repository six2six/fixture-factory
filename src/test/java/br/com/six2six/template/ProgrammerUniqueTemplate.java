package br.com.six2six.template;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import br.com.six2six.fixturefactory.model.Programmer;
import br.com.six2six.fixturefactory.model.Skill;

public class ProgrammerUniqueTemplate implements TemplateLoader {
    @Override
    public void load() {
        Fixture.of(Programmer.class).addTemplate("valid", new Rule() {{
            add("name", firstName());
            add("skills", has(2).unique().of(Skill.class, "valid"));
        }});

        Fixture.of(Skill.class).addTemplate("valid", new Rule() {{
            add("name", random("Java", "JavaScript"));
        }});
    }
}
