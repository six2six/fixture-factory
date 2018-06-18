package br.com.six2six.fixturefactory;

import java.util.LinkedHashMap;
import java.util.Map;

public class TemplateHolder {

	private Class<?> clazz;
	
	private Map<String, Rule> rules = new LinkedHashMap<String, Rule>();
	
	public TemplateHolder(Class<?> clazz) {
		this.clazz = clazz;
	}

    public TemplateHolder addTemplate(Enum<?> label, Rule rule) {
	    return addTemplate(getLabel(label), rule);
    }

	public TemplateHolder addTemplate(String label, Rule rule) {
		rules.put(label, rule);
		return this;
	}

    public ExtendedTemplateHolder addTemplate(Enum<?> label) {
	    return addTemplate(getLabel(label));
    }

    public ExtendedTemplateHolder addTemplate(String label) {
        return new ExtendedTemplateHolder(this, label);
    }

	public Class<?> getClazz() {
		return clazz;
	}

	public Map<String, Rule> getRules() {
		return rules;
	}

    private String getLabel(Enum<?> label) {
        return label.toString();
    }
}
