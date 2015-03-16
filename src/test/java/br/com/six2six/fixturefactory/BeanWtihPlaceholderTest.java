package br.com.six2six.fixturefactory;

import static java.lang.String.format;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import org.junit.BeforeClass;
import org.junit.Test;

import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import br.com.six2six.fixturefactory.model.BeanWithPlaceholder;

public class BeanWtihPlaceholderTest {

    @BeforeClass
    public static void setUp() {
        FixtureFactoryLoader.loadTemplates("br.com.six2six.template");
    }
    
    @Test
    public void shouldSubstituteOnePlaceholderViaConstructor() {
        BeanWithPlaceholder result = Fixture.from(BeanWithPlaceholder.Immutable.class).gimme("one-placeholder");
        assertThat(result.getAttrThree(), equalTo(result.getAttrOne()));
    }
    
    @Test
    public void shouldSubstituteOnePlaceholderViaSetter() {
        BeanWithPlaceholder result = Fixture.from(BeanWithPlaceholder.Mutable.class).gimme("one-placeholder");
        assertThat(result.getAttrThree(), equalTo(result.getAttrOne()));
    }    
    
    @Test
    public void shouldSubstituteTwoPlaceholdersViaConstructor() {
        BeanWithPlaceholder result = Fixture.from(BeanWithPlaceholder.Immutable.class).gimme("two-placeholders");
        assertThat(result.getAttrThree(), equalTo(format("%s %s", result.getAttrOne(), result.getAttrTwo())));
    }
    
    @Test
    public void shouldSubstituteTwoPlaceholdersViaSetters() {
        BeanWithPlaceholder result = Fixture.from(BeanWithPlaceholder.Mutable.class).gimme("two-placeholders");
        assertThat(result.getAttrThree(), equalTo(format("%s %s", result.getAttrOne(), result.getAttrTwo())));
    }   
    
    @Test
    public void shouldSubstituteTheSamePlaceholdersTwiceViaConstructor() {
        BeanWithPlaceholder result = Fixture.from(BeanWithPlaceholder.Immutable.class).gimme("same-placeholder-twice");
        assertThat(result.getAttrThree(), equalTo(format("%s %s", result.getAttrOne(), result.getAttrOne())));
    }
    
    @Test
    public void shouldSubstituteTheSamePlaceholdersTwiceViaSetters() {
        BeanWithPlaceholder result = Fixture.from(BeanWithPlaceholder.Mutable.class).gimme("same-placeholder-twice");
        assertThat(result.getAttrThree(), equalTo(format("%s %s", result.getAttrOne(), result.getAttrOne())));
    }     
}
