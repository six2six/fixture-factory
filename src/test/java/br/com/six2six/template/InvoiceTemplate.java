package br.com.six2six.template;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import br.com.six2six.fixturefactory.model.Invoice;

public class InvoiceTemplate implements TemplateLoader {

	@Override
	public void load() {
		Fixture.of(Invoice.class).addTemplate("valid", new Rule(){{
			add("id", regex("\\d{3,5}"));
			add("ammount", random(new BigDecimal("58.67"), new BigDecimal("45.89")));
			add("dueDate", beforeDate("2011-04-08", new SimpleDateFormat("yyyy-MM-dd")));
			
		}}).addTemplate("previousInvoices", new Rule(){{
			add("id", regex("\\d{3,5}"));
			add("ammount", random(new BigDecimal("58.67"), new BigDecimal("45.89")));
			add("dueDate", sequenceDate("2011-04-01", new SimpleDateFormat("yyyy-MM-dd"), decrement(1).month()));
			
		}}).addTemplate("nextInvoices", new Rule(){{
			add("id", regex("\\d{3,5}"));
			add("ammount", random(new BigDecimal("58.67"), new BigDecimal("45.89")));
			add("dueDate", sequenceDate("2011-04-30", increment(1).day()));
		}});
	}
}
