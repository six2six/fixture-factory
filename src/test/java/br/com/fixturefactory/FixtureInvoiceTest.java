package br.com.fixturefactory;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import br.com.fixturefactory.loader.FixtureFactoryLoader;
import br.com.fixturefactory.model.Invoice;
import br.com.fixturefactory.util.DateTimeUtil;

public class FixtureInvoiceTest {

	@BeforeClass
	public static void setUp() {
		FixtureFactoryLoader.loadTemplates("br.com.fixturefactory.template");
	}

	@Test
	public void fixtureInvoice() {
		Invoice invoice = Fixture.from(Invoice.class).gimme("valid");
		assertNotNull("Invoice should not be null", invoice);
	}
	
	@Test
	public void fixturePreviousInvoices() {
		List<Invoice> invoices = Fixture.from(Invoice.class).gimme(3, "previousInvoices");
		assertNotNull("Invoice list should not be null", invoices);
		assertTrue("Invoice list should not be empty", !invoices.isEmpty());
		
		Calendar calendar = DateTimeUtil.toCalendar("2011-04-01", new SimpleDateFormat("yyyy-MM-dd"));
		
		for (Invoice invoice : invoices) {
			assertEquals("Calendar should be equal", calendar, invoice.getDueDate());
			calendar.add(Calendar.MONTH, -1);
		}
	}
	
	@Test
	public void fixtureNextInvoices() {
		List<Invoice> invoices = Fixture.from(Invoice.class).gimme(3, "nextInvoices");
		assertNotNull("Invoice list should not be null", invoices);
		assertTrue("Invoice list should not be empty", !invoices.isEmpty());
		
		Calendar calendar = DateTimeUtil.toCalendar("2011-04-30", new SimpleDateFormat("yyyy-MM-dd"));
		
		for (Invoice invoice : invoices) {
			assertEquals("Calendar should be equal", calendar, invoice.getDueDate());
			calendar.add(Calendar.DAY_OF_MONTH, 1);
		}
	}
}
