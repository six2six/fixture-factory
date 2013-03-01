package br.com.fixturefactory.template;

import br.com.fixturefactory.Fixture;
import br.com.fixturefactory.Rule;
import br.com.fixturefactory.loader.TemplateLoader;
import br.com.fixturefactory.model.XMLTransaction;

public class XMLTransactionTemplate implements TemplateLoader {

	@Override
	public void load() {
		Fixture.of(XMLTransaction.class).addTemplate("validTransaction", new Rule(){{
			add("origin", "ORIGIN");
			add("date", instant("now"));
			add("hour", instant("now"));
		}});
	}
}
