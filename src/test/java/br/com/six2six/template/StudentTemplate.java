package br.com.six2six.template;

import br.com.six2six.bfgex.Gender;
import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.base.Sequence;
import br.com.six2six.fixturefactory.function.impl.NumberSequence;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import br.com.six2six.fixturefactory.model.Student;

public class StudentTemplate implements TemplateLoader {

	@Override
	public void load() {
		Fixture.of(Student.class).addTemplate("valid", new Rule(){{
			add("id", sequence(1L, 1));
			add("firstName", firstName());
			add("lastName", lastName());
			add("gender", random(Gender.class));
			add("bestScore", regex("\\d{2}\\.\\d{3}"));
			add("testsTaken", regex("\\d{1}1"));
			add("idCardNumber", regex("\\d{6}"));
		}}
		).addTemplate("validFemaleStudent", new Rule(){{
			add("id", sequence(200L, 2));
			add("firstName", firstName(Gender.FEMALE));
			add("lastName", lastName());
			add("gender", Gender.FEMALE);
		}}
		).addTemplate("validMaleStudent", new Rule() {{
			add("id", regex("\\d{3,5}"));
			add("firstName", firstName(Gender.MALE));
			add("lastName", lastName());
			add("gender", Gender.MALE);
		}});
		
		final Sequence<Number> numberSequence = new NumberSequence(1L, 1);
		
		Fixture.of(Student.class).addTemplate("sharedSequence", new Rule(){{ 
			add("id", sequence(numberSequence));
		}}
		).addTemplate("otherSharedSequence", new Rule(){{ 
			add("id", sequence(numberSequence));
		}});
		
		Fixture.of(Student.class).addTemplate("defaultNumberSequence", new Rule() {{
			add("id", sequence(Long.class));
		}});	
	}
}
