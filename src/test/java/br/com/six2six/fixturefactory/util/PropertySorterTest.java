package br.com.six2six.fixturefactory.util;

import br.com.six2six.fixturefactory.Property;
import org.junit.Test;

import java.util.LinkedHashSet;
import java.util.Set;

import static org.hamcrest.Matchers.contains;
import static org.junit.Assert.assertThat;

public class PropertySorterTest {

    @Test
    public void shouldSortWithDependencies() throws Exception {
        final Property firstName = new Property("firstName", "diego");
        final Property lastName = new Property("lastName", "domingues");
        final Property email = new Property("email", "${firstName}.${lastName}@gmail.com");
        final Property email_confirm = new Property("email_confirmation", "${email}");

        Set<Property> sortedProperties = new PropertySorter(new LinkedHashSet<Property>(){{
            add(email_confirm);
            add(firstName);
            add(lastName);
            add(email);
        }}).sort();

        assertThat(sortedProperties, contains(firstName, lastName, email, email_confirm));
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionWhenThereIsCyclicDependency() throws Exception {
        final Property name = new Property("name", "diego");
        final Property email = new Property("email", "${name_confirmation}");
        final Property email_confirm = new Property("email_confirmation", "${email}");

        new PropertySorter(new LinkedHashSet<Property>(){{
            add(email_confirm);
            add(name);
            add(email);
        }}).sort();
    }
}