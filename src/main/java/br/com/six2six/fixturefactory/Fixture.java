package br.com.six2six.fixturefactory;

import java.util.LinkedHashMap;
import java.util.Map;

public final class Fixture {

	private Fixture() throws InstantiationException {
		throw new InstantiationException("The class can't be instantiated");
	}

	private static Map<Class<?>, TemplateHolder> templates = new LinkedHashMap<Class<?>, TemplateHolder>();
	
	public static TemplateHolder of(Class<?> clazz) {
		TemplateHolder template = templates.get(clazz);
		
		if (template == null) {
			template = new TemplateHolder(clazz);
			templates.put(clazz, template);
		}

		return template;
	}
	
	public static ObjectFactory from(Class<?> clazz) {
		return new ObjectFactory(of(clazz));
	}
}
