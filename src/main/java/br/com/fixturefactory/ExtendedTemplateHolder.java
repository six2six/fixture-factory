package br.com.fixturefactory;

public class ExtendedTemplateHolder {
	private final TemplateHolder templateHolder;
	private final Rule baseTemplateRule;
	
	public ExtendedTemplateHolder(TemplateHolder templateHolder, String baseTemplateLabel) {
		this.templateHolder = templateHolder;
		this.baseTemplateRule = templateHolder.getRules().get(baseTemplateLabel);
	}
	
	public TemplateHolder inherits(String label, Rule extendedRule) {
		templateHolder.getRules().put(label, new Rule(baseTemplateRule, extendedRule));
		return templateHolder;
	}
}
