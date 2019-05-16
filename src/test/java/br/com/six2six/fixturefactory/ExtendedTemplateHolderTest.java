package br.com.six2six.fixturefactory;

import br.com.six2six.fixturefactory.model.Order;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.assertNotNull;

public class ExtendedTemplateHolderTest {

    @Test
    public void enumLabel(){
        Rule rule = new Rule();
        TemplateHolder templateHolder = new TemplateHolder(Order.class);
        templateHolder.addTemplate(MyEnum.VALUE_X, rule);

        ExtendedTemplateHolder client = new ExtendedTemplateHolder(templateHolder, MyEnum.VALUE_X);
        templateHolder = client.inherits(MyEnum.VALUE_X, rule);
        Map<String, Rule> rules = templateHolder.getRules();

        Rule ruleX = rules.get("VALUE_X");
        assertNotNull(ruleX);
    }

    @Test
    public void stringLabel(){
        Rule rule = new Rule();
        TemplateHolder templateHolder = new TemplateHolder(Order.class);
        templateHolder.addTemplate("VALUE_X", rule);

        ExtendedTemplateHolder client = new ExtendedTemplateHolder(templateHolder, MyEnum.VALUE_X);
        templateHolder = client.inherits("VALUE_X", rule);
        Map<String, Rule> rules = templateHolder.getRules();

        Rule ruleX = rules.get("VALUE_X");
        assertNotNull(ruleX);
    }

    enum MyEnum {
        VALUE_X,
        VALUE_Y,
        VALUE_Z
    }

}
