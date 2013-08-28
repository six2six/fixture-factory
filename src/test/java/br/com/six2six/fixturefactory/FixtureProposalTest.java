package br.com.six2six.fixturefactory;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import br.com.six2six.fixturefactory.model.Document;
import br.com.six2six.fixturefactory.model.SimpleProposal;

public class FixtureProposalTest {
	
	@Before
	public void before() {
		
		Fixture.of(SimpleProposal.class).addTemplate("valid", new Rule(){{
			add("id", "123456");
			add("contract.holder.document", new Document("10101010"));
		}});
		
	}
	
	@Test
	public void test() {
		SimpleProposal proposal = Fixture.from(SimpleProposal.class).gimme("valid");
		Assert.assertNotNull(proposal);
		Assert.assertNotNull(proposal.getContract().getHolder().getDocument());
	}

}
