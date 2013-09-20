package br.com.six2six.fixturefactory.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import net.vidageek.mirror.dsl.Mirror;
import net.vidageek.mirror.list.dsl.Matcher;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.PropertyUtilsBean;
import org.apache.commons.lang.StringUtils;

import com.thoughtworks.paranamer.AdaptiveParanamer;
import com.thoughtworks.paranamer.Paranamer;

public class ReflectionUtils {

    public static final String CGLIB_CLASS_SEPARATOR = "$$";
    private static final String NO_SUCH_ATTRIBUTE_MESSAGE = "%s-> No such attribute: %s[%s]";
    
    @SuppressWarnings("unchecked")
    public static <T> T cast(Object source){
        try {
            return source == null ? null : (T) source;
        } catch (Exception e) {
            throw new IllegalArgumentException("Cannot convert to type");
        }
    }
    
    @SuppressWarnings("unchecked")
    public static <T> T invokeGetter(Object bean, String attribute) {
        return (T) ReflectionUtils.invokeGetter(bean, attribute, true);
    }
    
    @SuppressWarnings("unchecked")
    public static <T> T invokeGetter(Object bean, String attribute, boolean fail) {
        try {
            return (T) getPropertyUtilsBean().getProperty(bean, attribute);
        }catch (Exception e) {
            if (fail) {
                throw new IllegalArgumentException("Error invoking get method for " + attribute);   
            } else {
                return null;
            }
        }
    }
    
    public static Object invokeRecursiveGetter(Object bean, String objectsPath) {
        Object lastValue = null;
        Object lastBean = bean;
        for (String propertyItem : objectsPath.split("\\.")) {
            lastValue = ReflectionUtils.invokeGetter(lastBean, propertyItem);
            lastBean = lastValue;
            if (lastValue == null) {
                break;
            }
        }
        return lastValue;
    }
    
    public static <T> void invokeSetter(Object bean, String attribute, Object value, boolean fail){
        try {
            new Mirror().on(bean).set().field(attribute).withValue(value);
        } catch (Exception ex){
            if(fail) {
                throw new IllegalArgumentException(String.format(NO_SUCH_ATTRIBUTE_MESSAGE, bean.getClass().getName(), attribute, value.getClass().getName()));
            }
        }   
    }
    
    public static <T> void invokeSetter(Object bean, String attribute, Object value){
        ReflectionUtils.invokeSetter(bean, attribute, value, true);
    }       
    
    public static void invokeRecursiveSetter(Object bean, String attribute, Object value) {
	    ReflectionUtils.invokeSetter(prepareInvokeRecursiveSetter(bean, attribute, value), attribute.substring(attribute.lastIndexOf(".") + 1), value, true);
	}
    
    public static Class<?> invokeRecursiveType(Class<?> clazz, String attribute) {
    	return invokeRecursiveField(clazz, attribute).getType();
    }

    public static Field invokeRecursiveField(Class<?> clazz, String attribute) {
        Field field = null;
        Class<?> targetBeanClass = getTargetClass(clazz);
        
        for (String propertyItem : attribute.split("\\.")) {
            field = new Mirror().on(targetBeanClass).reflect().field(propertyItem);
            if (field == null) throw new IllegalArgumentException(String.format("%s-> Field %s doesn't exists", clazz.getName(), attribute));
            targetBeanClass = field.getType();
        }

        return field;
    }

    @SuppressWarnings("unchecked")
    public static <T> T newInstance(Class<?> clazz) {
        return (T) newInstance(clazz, Collections.emptyList());
    }
    
    public static <T> T newInstance(Class<T> target, List<? extends Object> parameters) {
		if (parameters.size() > 0) {
			return new Mirror().on(target).invoke().constructor().withArgs(parameters.toArray());			
		} else {
			return new Mirror().on(target).invoke().constructor().withoutArgs();
		}
	}
    
    public static <T> List<String> filterConstructorParameters(Class<T> target, Collection<String> names) {
		List<String> result = Collections.emptyList();
		Paranamer paranamer = new AdaptiveParanamer();
		
		for (Constructor<T> constructor : new Mirror().on(target).reflectAll().constructors()) {
			List<String> constructorParameterNames = Arrays.asList(paranamer.lookupParameterNames(constructor, false));
			if (result.size() < constructorParameterNames.size()) {
				if (names.containsAll(constructorParameterNames) 
						&& constructorParameterTypesMatch(target, constructorParameterNames, Arrays.asList(constructor.getParameterTypes())))
					result = constructorParameterNames;
			}
		}
		return result;
    }
    
    private static boolean constructorParameterTypesMatch(Class<?> target, List<String> parameterNames, List<Class<?>> parameterTypes) {
    	for (int idx = 0; idx < parameterNames.size(); idx++) {
    		String parameterName = parameterNames.get(idx);
    		Class<?> parameterType = parameterTypes.get(idx);
    		if (isInnerClass(target) && parameterType.equals(target.getEnclosingClass())) continue;
			if (!parameterType.equals(invokeRecursiveType(target, parameterName))) return false;
    	}
    	return true;
    }
    
    public static Class<?> getTargetClass(Class<?> clazz) {
        if (isCglibProxy(clazz) || Proxy.isProxyClass(clazz)) {
            clazz = clazz.getSuperclass();
        }
        return clazz;
    }
    
    public static boolean isCglibProxy(Class<?> clazz) {
        return (clazz != null && clazz.getName().indexOf(CGLIB_CLASS_SEPARATOR) != -1);
    }
    
    public static PropertyUtilsBean getPropertyUtilsBean() {
    	return BeanUtilsBean.getInstance().getPropertyUtils();
    }
 
    private static Object prepareInvokeRecursiveSetter(Object bean, String attribute, Object value) {
        Object targetBean = bean;
        Object lastBean = null;
        
        int lastAttributeIdx = attribute.lastIndexOf(".");
        
        String path  = null;
        if (lastAttributeIdx > 0) {
            path = StringUtils.defaultIfEmpty(attribute.substring(0, lastAttributeIdx), null);
        }
        
        if (path != null) {
            for (String propertyItem : path.split("\\.")) {
                lastBean = targetBean;
                targetBean = ReflectionUtils.invokeGetter(targetBean, propertyItem);
                if(targetBean == null) {
                	Class<?> type = invokeRecursiveType(lastBean.getClass(), propertyItem);
                    try {
                        List<Object> args = new ArrayList<Object>();
                        if (isInnerClass(type)) {
                            args.add(lastBean);
                        }
                        targetBean = newInstance(type, args);
                        ReflectionUtils.invokeSetter(lastBean, propertyItem, targetBean, true);                     
                    } catch (Exception e) {
                        throw new IllegalArgumentException(String.format(NO_SUCH_ATTRIBUTE_MESSAGE, lastBean.getClass().getName(), propertyItem, type.getName()));
                    }
                }
            }
        }
        
        return targetBean;
    }
    
    public static boolean isInnerClass(Class<?> clazz) {
    	return clazz.getEnclosingClass() != null && !Modifier.isStatic(clazz.getModifiers());
    }
    
    @SuppressWarnings("unchecked")
	public static <T,U> Collection<U> map(Collection<T> collection, String propertyName) {
    	Collection<U> map = null; 
	    try {
	         map = collection.getClass().newInstance();    
        } catch (Exception e) {
            map = new ArrayList<U>();
        }
	    
    	for (T item : collection) {
    		map.add((U) ReflectionUtils.invokeGetter(item, propertyName, true));
		};
		return map;
    }

	public static <T> boolean hasDefaultConstructor(final Class<T> clazz) {
		return !new Mirror().on(clazz).reflectAll().constructors().matching(new Matcher<Constructor<T>>() {
			@Override
			public boolean accepts(Constructor<T> constructor) {
				if (ReflectionUtils.isInnerClass(clazz)) {
					return constructor.getParameterTypes().length == 1 && constructor.getParameterTypes()[0].equals(clazz.getEnclosingClass());
				}
				return Arrays.asList(constructor.getParameterTypes()).isEmpty();
			}
		}).isEmpty();
	}
}
