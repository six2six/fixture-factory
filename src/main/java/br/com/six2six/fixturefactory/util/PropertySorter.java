package br.com.six2six.fixturefactory.util;

import br.com.six2six.fixturefactory.Property;
import br.com.six2six.fixturefactory.function.impl.IdentityFunction;

import java.util.*;
import java.util.regex.Matcher;

import static br.com.six2six.fixturefactory.transformer.PlaceholderTransformer.PLACEHOLDER;

public class PropertySorter {

    private static final String CYCLIC_MESSAGE_ERROR = "Fixture Factory failed to calculate ${} field references. Either a cyclic dependency exists or one of the fields references a non-existent field.";
    private final Map<String, Property> mapedProperties;
    private List<Property> independents;
    private final Map<Property, Set<Property>> incoming;
    private final Map<Property, Set<Property>> outcoming;
    private Set<Property> sortedProperties;

    public PropertySorter(Set<Property> properties) {
        mapedProperties = mapProperties(properties);
        incoming = new LinkedHashMap<Property, Set<Property>>();
        outcoming = new LinkedHashMap<Property, Set<Property>>();
    }

    public Set<Property> sort() {
        extractDependencies();
        sortDependencies();

        if (allDependenciesSorted()) {
            return sortedProperties;
        } else {
            throw new IllegalArgumentException(CYCLIC_MESSAGE_ERROR);
        }
    }

    private boolean allDependenciesSorted() {
        return incoming.isEmpty() && outcoming.isEmpty();
    }

    private void sortDependencies() {
        sortedProperties = new LinkedHashSet<Property>();

        while (!independents.isEmpty()) {
            Property independentProperty = independents.remove(0);

            sortedProperties.add(independentProperty);

            Set<Property> dependents = outcoming.get(independentProperty);
            if (dependents != null) {

                for (Property dependentProperty : dependents) {
                    Set<Property> incomingProperty = incoming.get(dependentProperty);
                    incomingProperty.remove(independentProperty);

                    if (incomingProperty.isEmpty()) {
                        independents.add(dependentProperty);
                        incoming.remove(dependentProperty);
                    }
                }

                outcoming.remove(independentProperty);
            }
        }
    }

    private void extractDependencies() {
        independents = new ArrayList<Property>();

        for (Property property : mapedProperties.values()) {

            if (property.getFunction() instanceof IdentityFunction) {

                Object value = property.getValue();

                if (value != null) {

                    Matcher matcher = PLACEHOLDER.matcher(value.toString());

                    while (matcher.find()) {

                        Property referencedProperty = mapedProperties.get(matcher.group(2));

                        getOrInitialize(outcoming, referencedProperty).add(property);

                        getOrInitialize(incoming, property).add(referencedProperty);
                    }

                    if (incoming.containsKey(property)) {
                        continue;
                    }
                }

            }

            independents.add(property);
        }
    }

    private Set<Property> getOrInitialize(Map<Property, Set<Property>> map, Property property) {
        if (!map.containsKey(property)) {
            map.put(property, new HashSet<Property>());
        }

        return map.get(property);
    }

    private Map<String, Property> mapProperties(Set<Property> properties) {
        Map<String, Property> propertiesMapped = new LinkedHashMap<String, Property>();

        for (Property property : properties) {
            propertiesMapped.put(property.getName(), property);
        }

        return propertiesMapped;
    }
}
