package br.com.fixturefactory;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import br.com.fixturefactory.model.Invoice;

public class FixtureInvoice {

	@Before
	public void setUp() {
		Fixture.of(Invoice.class).addTemplate("valid", new Rule(){{
			add("id", regex("\\d{3,5}"));
			add("ammount", random(new BigDecimal("58.67"), new BigDecimal("45.89")));
			add("dueDate", beforeDate("2011-04-08", new SimpleDateFormat("yyyy-MM-dd")));
		}});
	}

	@Test
	public void fixtureInvoice() {
		Invoice invoice = Fixture.of(Invoice.class).gimme("valid");
		Assert.assertNotNull("Invoice should not be null", invoice);
	}
	
}
