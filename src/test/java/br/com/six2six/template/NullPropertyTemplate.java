package br.com.six2six.template;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import br.com.six2six.fixturefactory.model.NullProperty;

public class NullPropertyTemplate implements TemplateLoader {

	@Override
	public void load() {
		Fixture.of(NullProperty.class)
		.addTemplate("allNames", new Rule(){{
			add("name", regex("\\w{8}"));
			add("otherName", regex("\\w{8}"));
		}})   
		.addTemplate("onlyName").inherits("allNames", new Rule(){{
			add("name", regex("\\w{8}"));
			add("otherName", null);
		}});   
	}
}
