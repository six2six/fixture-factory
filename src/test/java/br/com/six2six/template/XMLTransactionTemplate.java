package br.com.six2six.template;

import br.com.fixturefactory.loader.TemplateLoader;
import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.model.XMLTransaction;

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
