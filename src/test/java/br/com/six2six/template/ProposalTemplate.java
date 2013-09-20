package br.com.six2six.template;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import br.com.six2six.fixturefactory.model.SimpleProposal;

public class ProposalTemplate implements TemplateLoader {

	@Override
	public void load() {
		Fixture.of(SimpleProposal.class).addTemplate("valid", new Rule(){{
			add("item.order.id", "123456");
		}});
	}

}
