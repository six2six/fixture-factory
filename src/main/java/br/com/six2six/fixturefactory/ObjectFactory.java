package br.com.six2six.fixturefactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import br.com.six2six.fixturefactory.processor.Processor;
import br.com.six2six.fixturefactory.transformer.CalendarTransformer;
import br.com.six2six.fixturefactory.transformer.ParameterPlaceholderTransformer;
import br.com.six2six.fixturefactory.transformer.PrimitiveTransformer;
import br.com.six2six.fixturefactory.transformer.PropertyPlaceholderTransformer;
import br.com.six2six.fixturefactory.transformer.SetTransformer;
import br.com.six2six.fixturefactory.transformer.Transformer;
import br.com.six2six.fixturefactory.transformer.TransformerChain;
import br.com.six2six.fixturefactory.transformer.WrapperTransformer;
import br.com.six2six.fixturefactory.util.ReflectionUtils;

public class ObjectFactory {
	
	private static final String NO_SUCH_LABEL_MESSAGE = "%s-> No such label: %s";
	private static final String LABELS_AMOUNT_DOES_NOT_MATCH = "%s-> labels amount does not match asked quantity (%s)";
	
	protected TemplateHolder templateHolder;
	
	private Object owner;

    private Processor processor;
	
	public ObjectFactory(TemplateHolder templateHolder) {
		this.templateHolder = templateHolder;
	}
	
	public ObjectFactory(TemplateHolder templateHolder, Object owner) {
		this.templateHolder = templateHolder;
		this.owner = owner;
	}

	public ObjectFactory(TemplateHolder templateHolder, Processor owner) {
	    this.templateHolder = templateHolder;
	    this.owner = owner;
	}

    public ObjectFactory uses(Processor processor) {
        this.processor = processor;
        return this;
    }
    
	@SuppressWarnings("unchecked")
	public <T> T gimme(String label) {
		Rule rule = findRule(label);

		return (T) this.createObject(rule);
	}

	@SuppressWarnings("unchecked")
	public <T> T gimme(String label, Rule propertiesToOverride) {
		Rule rule = findRule(label);

		return (T) this.createObject(new Rule(rule, propertiesToOverride));
	}

	public <T> List<T> gimme(Integer quantity, String label) {
		Rule rule = findRule(label);

		return this.createObjects(quantity, rule);
	}
	
	public <T> List<T> gimme(Integer quantity, String... labels) {
		return gimme(quantity, Arrays.asList(labels));
	}
	
	public <T> List<T> gimme(Integer quantity, List<String> labels) {
		if(labels.size() != quantity) throw new IllegalArgumentException(String.format(LABELS_AMOUNT_DOES_NOT_MATCH, templateHolder.getClazz().getName(), StringUtils.join(labels, ",")));
		
		List<Rule> rules = findRules(labels);
		
		return createObjects(quantity, rules);
	}

	public <T> List<T> gimme(int quantity, String label, Rule propertiesToOverride) {
		Rule rule = findRule(label);

		return this.createObjects(quantity, new Rule(rule, propertiesToOverride));
	}

	protected Object createObject(Rule rule) {
		Map<String, Object> constructorArguments = new HashMap<String, Object>();
		List<Property> deferredProperties = new ArrayList<Property>();
		Class<?> clazz = templateHolder.getClazz();
		
		List<String> parameterNames = !ReflectionUtils.hasDefaultConstructor(clazz) ? 
										lookupConstructorParameterNames(clazz, rule.getProperties()) : new ArrayList<String>();
		
		for (Property property : rule.getProperties()) {
			if (parameterNames.contains(property.getRootAttribute())) {
				constructorArguments.put(property.getName(), generateConstructorParamValue(property));
			} else {
				deferredProperties.add(property);
			}
		}
		
		Object result = ReflectionUtils.newInstance(clazz, processConstructorArguments(parameterNames, constructorArguments));
		
		for (Property property : deferredProperties) {
			ReflectionUtils.invokeRecursiveSetter(result, property.getName(), processPropertyValue(result, property));
		}
		
		if (processor != null) {
		    processor.execute(result);
		}
		return result;
	}
	
	@SuppressWarnings("unchecked")
	protected <T> List<T> createObjects(int quantity, Rule rule) {
		List<T> results = new ArrayList<T>(quantity);
		for (int i = 0; i < quantity; i++) {
			results.add((T) this.createObject(rule));
		}	

		return results;
	}
	
	@SuppressWarnings("unchecked")
	protected <T> List<T> createObjects(int quantity, List<Rule> rules) {
		List<T> results = new ArrayList<T>(quantity);
		for (int i = 0; i < quantity; i++) {
			results.add((T) this.createObject(rules.get(i)));
		}	

		return results;
	}

	private Rule findRule(String label) {
		Rule rule = templateHolder.getRules().get(label);

		if (rule == null) {
			throw new IllegalArgumentException(String.format(NO_SUCH_LABEL_MESSAGE, templateHolder.getClazz().getName(), label));
		}

		return rule;
	}
	
	private List<Rule> findRules(List<String> labels) {
		List<Rule> rules = new ArrayList<Rule>();
		
		for(String label : labels) {
			Rule rule = templateHolder.getRules().get(label);
			if(rule == null) throw new IllegalArgumentException(String.format(NO_SUCH_LABEL_MESSAGE, templateHolder.getClazz().getName(), label));
			
			rules.add(rule);
		}
		
		return rules;
	}

	private Object generateConstructorParamValue(Property property) {
	    if (property.hasRelationFunction() && processor != null) {
	        return property.getValue(processor);
	    } else {
	        return property.getValue();
	    }
	}

	protected List<Object> processConstructorArguments(List<String> parameterNames, Map<String, Object> arguments) {
		List<Object> values = new ArrayList<Object>();
		
		if (owner != null && ReflectionUtils.isInnerClass(templateHolder.getClazz()))  {
			values.add(owner);	
		}
		
        TransformerChain transformerChain = buildTransformerChain(new ParameterPlaceholderTransformer(arguments));
		
		for (String parameterName : parameterNames) {
			Class<?> fieldType = ReflectionUtils.invokeRecursiveType(templateHolder.getClazz(), parameterName);
			Object result = arguments.get(parameterName);
			if (result == null) {
				result = processChainedProperty(parameterName, fieldType, arguments);	
			}
			values.add(transformerChain.transform(result, fieldType));
		}
		return values;
	}

	protected Object processChainedProperty(String parameterName, Class<?> fieldType, Map<String, Object> arguments) {
		Rule rule = new Rule();
		for (final String argument : arguments.keySet()) {
			int index = argument.indexOf(".");
			if (index > 0 && argument.substring(0, index).equals(parameterName)) {
				rule.add(argument.substring(index+1), arguments.get(argument));
			}
		}
		return new ObjectFactory(new TemplateHolder(fieldType), processor).createObject(rule);
	}

	protected Object processPropertyValue(Object object, Property property) {
		Class<?> fieldType = ReflectionUtils.invokeRecursiveType(object.getClass(), property.getName());
		
		Object value = null;
		if (property.hasRelationFunction() || ReflectionUtils.isInnerClass(fieldType)) {
		    value = processor != null ? property.getValue(object, processor) : property.getValue(object);
		} else {
		    value = property.getValue();
		}
	    TransformerChain transformerChain = buildTransformerChain(new PropertyPlaceholderTransformer(object));
		return transformerChain.transform(value, fieldType);
	}
	
	protected <T> List<String> lookupConstructorParameterNames(Class<T> target, Set<Property> properties) {
		Collection<String> propertyNames = ReflectionUtils.map(properties, "rootAttribute");
		return ReflectionUtils.filterConstructorParameters(target, propertyNames);
	}
	
	protected TransformerChain buildTransformerChain(Transformer transformer) {
	    TransformerChain transformerChain = new TransformerChain(transformer);
	    
	    transformerChain.add(new CalendarTransformer());
        transformerChain.add(new SetTransformer());
        transformerChain.add(new PrimitiveTransformer());
        transformerChain.add(new WrapperTransformer());
        
        return transformerChain;
	}
	
}
