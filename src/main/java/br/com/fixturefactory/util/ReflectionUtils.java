package br.com.fixturefactory.util;

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

import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.PropertyUtilsBean;
import org.apache.commons.lang.StringUtils;

import com.thoughtworks.paranamer.AdaptiveParanamer;
import com.thoughtworks.paranamer.Paranamer;

public class ReflectionUtils {

    public static final String CGLIB_CLASS_SEPARATOR = "$$";
    
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
            getPropertyUtilsBean().setProperty(bean, attribute, value);
        } catch (Exception ex){
            if(fail) {
                throw new IllegalArgumentException(bean.getClass().getCanonicalName() + "-> No such attribute: " + attribute + "[" + value.getClass().getName() + "]");
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
        Class<?> superClass = null;
        Class<?> targetBeanClass = getTargetClass(clazz);
        
        for (String propertyItem : attribute.split("\\.")) {
            do {
                superClass = targetBeanClass.getSuperclass();
                try {
                    field = targetBeanClass.getDeclaredField(propertyItem);
                    targetBeanClass = field.getType();
                } catch (NoSuchFieldException e) {
                    targetBeanClass = superClass;
                } 
            } while (superClass != null && !superClass.equals(Object.class));
        }
        
        if (field == null) {
            throw new IllegalArgumentException("Field " + attribute + " doesn't exists");
        }
        
        return field;
    }

    @SuppressWarnings("unchecked")
    public static <T> T newInstance(Class<?> clazz) {
        return (T) newInstance(clazz, Collections.emptyList());
    }
    
    @SuppressWarnings("unchecked")
    public static <T> T newInstance(Class<?> clazz, Object... params) {
        Object instance = null;
        try {
            Constructor<?> constructor = clazz.getDeclaredConstructor(getClasses(params));
            constructor.setAccessible(true);
            instance = constructor.newInstance(params);
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
        return (T) instance;
    }
    
    private static Class<?>[] getClasses(Object... params) {
        Class<?>[] classes = new Class<?>[params.length];
        for(int i = 0; i < params.length; i ++) {
        classes[i] = params[i].getClass();
        }
        return classes;
       }

    public static <T> T newInstance(Class<T> target, List<Object> parameters) {
		if (parameters.size() > 0) {
			return new Mirror().on(target).invoke().constructor().withArgs(parameters.toArray());			
		} else {
			return new Mirror().on(target).invoke().constructor().withoutArgs();
		}
	}
    
    public static <T> List<String> filterConstructorParameterNames(Class<T> target, Collection<String> names) {
		List<String> result = Collections.emptyList();
		Paranamer paranamer = new AdaptiveParanamer();
		
		for (Constructor<T> constructor : new Mirror().on(target).reflectAll().constructors()) {
			List<String> constructorParameterNames = Arrays.asList(paranamer.lookupParameterNames(constructor, false));
			if (result.size() < constructorParameterNames.size() && names.containsAll(constructorParameterNames)) {
				result = constructorParameterNames;
			}
		}
		
		return result;
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
                    try {
                        Class<?> type = invokeRecursiveType(lastBean.getClass(), propertyItem);
                        List<Object> args = new ArrayList<Object>();
                        if (isInnerClass(type)) {
                            args.add(lastBean);
                        }
                        targetBean = newInstance(type, args);
                        ReflectionUtils.invokeSetter(lastBean, propertyItem, targetBean, true);                     
                    } catch (Exception e) {
                        throw new IllegalArgumentException("No such attribute: " + propertyItem + " declared in class " + lastBean.getClass().getCanonicalName());
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
	         map = (Collection<U>) collection.getClass().newInstance();    
        } catch (Exception e) {
            map = new ArrayList<U>();
        }
	    
    	for (T item : collection) {
    		map.add((U) ReflectionUtils.invokeGetter(item, propertyName, true));
		};
		return map;
    }
}
