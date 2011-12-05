package br.com.fixturefactory;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import br.com.fixturefactory.model.Item;
import br.com.fixturefactory.model.Order;
import br.com.fixturefactory.model.Payment;

public class FixtureCircularReferenceTest {

	@Before
	public void setUp() {
		Fixture.of(Order.class).addTemplate("valid", new Rule(){{
			add("id", random(Long.class, range(1L, 200L)));
			add("items", association(Item.class, 3, "valid", "order"));
			add("payment", association(Payment.class, "valid", "order"));
		}});

		Fixture.of(Item.class).addTemplate("valid", new Rule(){{
			add("productId", random(Integer.class, range(1L, 200L)));
		}});

		Fixture.of(Payment.class).addTemplate("valid", new Rule(){{
			add("id", random(Long.class, range(1L, 200L)));
		}});
		
	}
	
	@Test
	public void circularReference() {
		Order order = Fixture.of(Order.class).gimme("valid");
		
		for (Item item : order.getItems()) {
			Assert.assertTrue("order relationship with item should have the same reference", item.getOrder() == order);
		}
		
		Assert.assertTrue("payment one-to-one relationship should have the same reference", order == order.getPayment().getOrder());
	}

}
