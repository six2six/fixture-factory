package br.com.six2six.template;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import br.com.six2six.fixturefactory.model.Item;
import br.com.six2six.fixturefactory.model.Order;
import br.com.six2six.fixturefactory.model.Payment;

public class FixtureCircularTemplate implements TemplateLoader {

	@Override
	public void load() {
		Fixture.of(Order.class).addTemplate("valid", new Rule(){{
			add("id", random(Long.class, range(1L, 200L)));
			add("registerDate", instant("1 day ago"));
			add("sendDate", instant("today"));
			add("items", has(3).of(Item.class, "valid"));
			add("payment", one(Payment.class, "valid"));
		}});

		Fixture.of(Order.class).addTemplate("otherValid", new Rule(){{
			add("id", random(Long.class, range(1L, 200L)));
			add("registerDate", instant("3 days ago"));
			add("sendDate", instant("tomorrow"));
			add("items", has(3).of(Item.class, "valid").targetAttribute("order"));
			add("payment", one(Payment.class, "valid").targetAttribute("order"));
		}});
		
		Fixture.of(Item.class).addTemplate("valid", new Rule(){{
			add("productId", random(Integer.class, range(1L, 200L)));
		}});

		Fixture.of(Payment.class).addTemplate("valid", new Rule(){{
			add("id", random(Long.class, range(1L, 200L)));
		}});
	}
}
