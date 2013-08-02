package br.com.six2six.template;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import br.com.six2six.fixturefactory.model.Owner;

public class InnerClassTemplate implements TemplateLoader {

	@Override
	public void load() {
		Fixture.of(Owner.class).addTemplate("valid", new Rule(){{
			add("inner", one(Owner.Inner.class, "valid"));
		}});

		Fixture.of(Owner.class).addTemplate("chained", new Rule(){{
		    add("inner.id", "333");
		}});

		Fixture.of(Owner.Inner.class).addTemplate("valid", new Rule(){{
			add("id", "222");
		}});
	}
}
