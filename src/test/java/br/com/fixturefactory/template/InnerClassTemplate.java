package br.com.fixturefactory.template;

import br.com.fixturefactory.Fixture;
import br.com.fixturefactory.Rule;
import br.com.fixturefactory.loader.TemplateLoader;
import br.com.fixturefactory.model.Owner;

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
