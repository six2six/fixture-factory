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
		if(baseRule == null) throw new IllegalArgumentException(String.format("%s-> No such template '%s' to be inherited.", templateHolder.getClazz().getName(), baseTemplateLabel));
		
		templateHolder.getRules().put(label, new Rule(baseRule, extendedRule));
		return templateHolder;
	}
}
