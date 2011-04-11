package br.com.fixturefactory;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import br.com.fixturefactory.model.Invoice;
import br.com.fixturefactory.util.DateTimeUtil;

public class FixtureInvoice {

	@Before
	public void setUp() {
		Fixture.of(Invoice.class).addTemplate("valid", new Rule(){{
			add("id", regex("\\d{3,5}"));
			add("ammount", random(new BigDecimal("58.67"), new BigDecimal("45.89")));
			add("dueDate", beforeDate("2011-04-08", new SimpleDateFormat("yyyy-MM-dd")));
			
		}}).addTemplate("previousInvoices", new Rule() {{
			add("id", regex("\\d{3,5}"));
			add("ammount", random(new BigDecimal("58.67"), new BigDecimal("45.89")));
			add("dueDate", sequence(startWith("2011-04-01", new SimpleDateFormat("yyyy-MM-dd"), decrementBy(1, Calendar.DAY_OF_MONTH))));
			
		}}).addTemplate("nextInvoices", new Rule() {{ 
			add("id", regex("\\d{3,5}"));
			add("ammount", random(new BigDecimal("58.67"), new BigDecimal("45.89")));
			add("dueDate", sequence(startWith("2011-04-30", new SimpleDateFormat("yyyy-MM-dd"), incrementBy(1, Calendar.DAY_OF_MONTH))));
		}});
	}

	@Test
	public void fixtureInvoice() {
		Invoice invoice = Fixture.of(Invoice.class).gimme("valid");
		Assert.assertNotNull("Invoice should not be null", invoice);
	}
	
	@Test
	public void fixturePreviousInvoices() {
		List<Invoice> invoices = Fixture.of(Invoice.class).gimme(3, "previousInvoices");
		Assert.assertNotNull("Invoice list should not be null", invoices);
		Assert.assertTrue("Invoice list should not be empty", !invoices.isEmpty());
		Calendar calendar = DateTimeUtil.toCalendar("2011-04-01", new SimpleDateFormat("yyyy-MM-dd"));
		for (Invoice invoice : invoices) {
			Assert.assertEquals("Calendar should be equal", calendar, invoice.getDueDate());
			calendar.add(Calendar.DAY_OF_MONTH, -1);
		}
	}
	
	@Test
	public void fixtureNextInvoices() {
		List<Invoice> invoices = Fixture.of(Invoice.class).gimme(3, "nextInvoices");
		Assert.assertNotNull("Invoice list should not be null", invoices);
		Assert.assertTrue("Invoice list should not be empty", !invoices.isEmpty());
		Calendar calendar = DateTimeUtil.toCalendar("2011-04-30", new SimpleDateFormat("yyyy-MM-dd"));
		for (Invoice invoice : invoices) {
			Assert.assertEquals("Calendar should be equal", calendar, invoice.getDueDate());
			calendar.add(Calendar.DAY_OF_MONTH, 1);
		}
	}
}
