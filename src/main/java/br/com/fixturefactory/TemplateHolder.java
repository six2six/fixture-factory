package br.com.fixturefactory;

import java.util.LinkedHashMap;
import java.util.Map;

public class TemplateHolder {

	private Class<?> clazz;
	
	private Map<String, Rule> rules = new LinkedHashMap<String, Rule>();
	
	public TemplateHolder(Class<?> clazz) {
		this.clazz = clazz;
	}

	public TemplateHolder addTemplate(String label, Rule rule) {
		rules.put(label, rule);
		return this;
	}
	
	public void addExtendedTemplate(String baseTemplateLabel, String label, Rule extendedRule) {
		rules.put(label, new Rule(rules.get(baseTemplateLabel), extendedRule));
	}

	public Class<?> getClazz() {
		return clazz;
	}

	public Map<String, Rule> getRules() {
		return rules;
	}
}
