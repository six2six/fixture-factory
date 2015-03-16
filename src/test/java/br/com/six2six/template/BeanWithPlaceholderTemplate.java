package br.com.six2six.template;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import br.com.six2six.fixturefactory.model.BeanWithPlaceholder;

public class BeanWithPlaceholderTemplate implements TemplateLoader {

    @Override
    public void load() {
        Fixture.of(BeanWithPlaceholder.Immutable.class).addTemplate("one-placeholder", new Rule(){{
            add("attrOne", "val1");
            add("attrTwo", "val2");
            add("attrThree", "${attrOne}");
        }});
        
        Fixture.of(BeanWithPlaceholder.Mutable.class).addTemplate("one-placeholder", new Rule(){{
            add("attrOne", "val1");
            add("attrTwo", "val2");
            add("attrThree", "${attrOne}");
        }});       
        
        Fixture.of(BeanWithPlaceholder.Immutable.class).addTemplate("two-placeholders", new Rule(){{
            add("attrOne", "val1");
            add("attrTwo", "val2");
            add("attrThree", "${attrOne} ${attrTwo}");
        }});
        
        Fixture.of(BeanWithPlaceholder.Mutable.class).addTemplate("two-placeholders", new Rule(){{
            add("attrOne", "val1");
            add("attrTwo", "val2");
            add("attrThree", "${attrOne} ${attrTwo}");
        }});
        
        Fixture.of(BeanWithPlaceholder.Immutable.class).addTemplate("same-placeholder-twice", new Rule(){{
            add("attrOne", "val1");
            add("attrTwo", "val2");
            add("attrThree", "${attrOne} ${attrOne}");
        }});
        
        Fixture.of(BeanWithPlaceholder.Mutable.class).addTemplate("same-placeholder-twice", new Rule(){{
            add("attrOne", "val1");
            add("attrTwo", "val2");
            add("attrThree", "${attrOne} ${attrOne}");
        }});              
    }
}
