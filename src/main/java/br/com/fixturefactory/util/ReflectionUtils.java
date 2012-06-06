package br.com.fixturefactory.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.Proxy;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.PropertyUtilsBean;
import org.apache.commons.lang.StringUtils;

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
                throw new IllegalArgumentException("No such attribute: " + attribute);
            }
        }   
    }
    
    public static <T> void invokeSetter(Object bean, String attribute, Object value){
        ReflectionUtils.invokeSetter(bean, attribute, value, true);
    }       
    
    public static void invokeRecursiveSetter(Object bean, String attribute, Object value) {
	    ReflectionUtils.invokeSetter(prepareInvokeRecursiveSetter(bean, attribute, value), attribute.substring(attribute.lastIndexOf(".") + 1), value, true);
	}
    
    public static Class<?> invokeRecursiveType(Object bean, String attribute) {
    	Field field = invokeRecursiveField(bean, attribute);
    	return field.getType();
    }

    public static Field invokeRecursiveField(Object bean, String attribute) {
        Field field = null;
        Class<?> superClass = null;
        Class<?> targetBeanClass = getTargetClass(bean.getClass());
        
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
        Object instance = null;
        try {
            Constructor<?> constructor = clazz.getDeclaredConstructor((Class<?>[])null);
            constructor.setAccessible(true);
            instance = constructor.newInstance((Object[])null);
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
        return (T) instance;
    }

    @SuppressWarnings("unchecked")
    public static <T> T newInnerClassInstance(Class<?> clazz, Object owner) {
    	Object instance = null;
    	try {
    		Constructor<?> constructor = clazz.getDeclaredConstructor(owner.getClass());
    		constructor.setAccessible(true);
    		instance = constructor.newInstance(owner);
    	} catch (Exception e) {
    		throw new IllegalArgumentException(e);
    	}

    	return (T) instance;
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
                        targetBean = newInstance(invokeRecursiveType(lastBean, propertyItem));
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
    
}
