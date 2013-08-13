package br.com.six2six.template;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import br.com.six2six.fixturefactory.model.Address;
import br.com.six2six.fixturefactory.model.City;
import br.com.six2six.fixturefactory.model.Client;

public class ClientTemplate implements TemplateLoader {

	@Override
	public void load() {
		Fixture.of(Client.class).addTemplate("valid", new Rule(){{
			add("id", random(Long.class, range(1L, 200L)));
			add("name", random("Anderson Parra", "Arthur Hirata"));
			add("nickname", random("nerd", "geek"));
			add("email", "${nickname}@gmail.com");
			add("birthday", instant("18 years ago"));
			add("address", one(Address.class, "valid"));
		}});
		
		Fixture.of(Address.class).addTemplate("valid", new Rule(){{
			add("id", random(Long.class, range(1L, 100L)));
			add("street", random("Paulista Avenue", "Ibirapuera Avenue"));
			add("city", one(City.class, "valid"));
			add("state", "${city}");
			add("country", "Brazil");
			add("zipCode", random("06608000", "17720000"));
		}});
		
		Fixture.of(Address.class).addTemplate("valid-augusta").inherits("valid", new Rule(){{
			add("street", "Augusta Street");
		}});
	}
}
