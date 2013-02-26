package br.com.fixturefactory;

public class ExtendedTemplateHolder {
	private final String label;
	private final TemplateHolder templateHolder;
	
	public ExtendedTemplateHolder(TemplateHolder templateHolder, String label) {
		this.templateHolder = templateHolder;
		this.label = label;
	}
	
	public TemplateHolder inherits(String baseTemplateLabel, Rule extendedRule) {
		Rule baseRule = templateHolder.getRules().get(baseTemplateLabel);
		
		if(baseRule == null) throw new IllegalArgumentException(templateHolder.getClazz().getName() + "-> No such template '" + baseTemplateLabel + "' to be inherited.");
		
		templateHolder.getRules().put(label, new Rule(baseRule, extendedRule));
		return templateHolder;
	}
}
