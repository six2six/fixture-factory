package br.com.fixturefactory.template;

import br.com.fixturefactory.Fixture;
import br.com.fixturefactory.Rule;
import br.com.fixturefactory.loader.TemplateLoader;
import br.com.fixturefactory.model.Item;
import br.com.fixturefactory.model.Order;
import br.com.fixturefactory.model.Payment;

public class FixtureCircularTemplate implements TemplateLoader {

	@Override
	public void load() {
		Fixture.of(Order.class).addTemplate("valid", new Rule(){{
			add("id", random(Long.class, range(1L, 200L)));
			add("items", has(3).of(Item.class, "valid"));
			add("payment", one(Payment.class, "valid"));
		}});

		Fixture.of(Order.class).addTemplate("otherValid", new Rule(){{
			add("id", random(Long.class, range(1L, 200L)));
			add("items", has(3).of(Item.class, "valid", "order"));
			add("payment", one(Payment.class, "valid", "order"));
		}});
		
		Fixture.of(Item.class).addTemplate("valid", new Rule(){{
			add("productId", random(Integer.class, range(1L, 200L)));
		}});

		Fixture.of(Payment.class).addTemplate("valid", new Rule(){{
			add("id", random(Long.class, range(1L, 200L)));
		}});
	}
}
