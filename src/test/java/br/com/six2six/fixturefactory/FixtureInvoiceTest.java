package br.com.six2six.fixturefactory;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import br.com.six2six.fixturefactory.model.Invoice;
import br.com.six2six.fixturefactory.util.DateTimeUtil;

public class FixtureInvoiceTest {

	@BeforeClass
	public static void setUp() {
		FixtureFactoryLoader.loadTemplates("br.com.six2six.template");
	}

	@Test
	public void fixtureInvoice() {
		Invoice invoice = Fixture.from(Invoice.class).gimme("valid");
		assertNotNull("Invoice should not be null", invoice);
		assertEquals("Invoice ammout should have precision of 2", 2, invoice.getAmmount().precision());
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
	        assertTrue("Invoice ammout should be within range", invoice.getAmmount().compareTo(new BigDecimal("45.89")) >= 0 && 
	                invoice.getAmmount().compareTo(new BigDecimal("58.67")) <= 0);			
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
