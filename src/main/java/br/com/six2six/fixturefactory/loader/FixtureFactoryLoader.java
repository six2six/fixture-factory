package br.com.six2six.fixturefactory.loader;

import static br.com.six2six.fixturefactory.util.ClassLoaderUtils.getClassesForPackage;

public final class FixtureFactoryLoader {

    private FixtureFactoryLoader() throws InstantiationException {
        throw new InstantiationException("The class can't be instantiated");
    }

	public static void loadTemplates(String basePackage) {
        for (Class<?> clazz : getClassesForPackage(basePackage)) {
            if (!clazz.isInterface() && TemplateLoader.class.isAssignableFrom(clazz)) {
                try {
                	((TemplateLoader) clazz.newInstance()).load();
                } catch (Exception e) {
                    throw new RuntimeException(String.format("template %s not loaded", clazz.getName()), e);
                }
            }
        } 
	}
}
