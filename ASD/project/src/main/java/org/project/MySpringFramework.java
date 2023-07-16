package org.project;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.project.annotations.Autowired;
import org.project.annotations.Qualifier;
import org.project.annotations.Service;
import org.reflections.Reflections;

public class MySpringFramework {
    private Map<String, Object> beans = new HashMap<>();

    public void scan(String basePackage) throws Exception {
        Reflections reflections = new Reflections(basePackage);

        Set<Class<?>> serviceTypes = reflections.getTypesAnnotatedWith(Service.class);
        for (Class<?> serviceClass : serviceTypes) {
            Constructor<?>[] constructors = serviceClass.getConstructors();

            if (constructors.length == 1 && constructors[0].getParameterCount() == 0) {
                Object instance = serviceClass.getDeclaredConstructor().newInstance();
                beans.put(serviceClass.getName(), instance);
            }

        }

        for (Class<?> serviceClass : serviceTypes) {
            performConstructorInjection(serviceClass);
            performSetterInjection(serviceClass);
        }
        performFieldInjection();
    }

    private void performConstructorInjection(Class<?> serviceClass)
            throws InstantiationException, IllegalAccessException, InvocationTargetException, IllegalArgumentException,
            NoSuchMethodException, SecurityException {
        Constructor<?>[] constructors = serviceClass.getDeclaredConstructors();

        for (Constructor<?> constructor : constructors) {
            if (constructor.isAnnotationPresent(Autowired.class)) {
                Class<?>[] parameterTypes = constructor.getParameterTypes();
                Object[] arguments = new Object[parameterTypes.length];
                for (int i = 0; i < parameterTypes.length; i++) {
                    arguments[i] = getBean(parameterTypes[i]);
                }
                Object instance = constructor.newInstance(arguments);
                beans.put(serviceClass.getName(), instance);
            }
        }
    }

    private void performSetterInjection(Class<?> serviceClass)
            throws IllegalAccessException, InvocationTargetException {
        Method[] methods = serviceClass.getDeclaredMethods();
        for (Method method : methods) {
            if (method.isAnnotationPresent(Autowired.class)) {
                Class<?>[] parameterTypes = method.getParameterTypes();
                if (parameterTypes.length == 1) {
                    Object argument = getBean(parameterTypes[0]);
                    if (argument != null) {
                        method.invoke(beans.get(serviceClass.getName()), argument);
                    }
                }
            }
        }
    }

    public void performFieldInjection() {
        try {
            for (Object serviceInstance : beans.values()) {
                Class<?> serviceClass = serviceInstance.getClass();
                // find annotated fields
                for (Field field : serviceClass.getDeclaredFields()) {
                    if (field.isAnnotationPresent(Autowired.class)) {
                        Qualifier qualifierAnnotation = field.getAnnotation(Qualifier.class);
                        String qualifierValue = qualifierAnnotation.value();

                        if (!qualifierValue.isEmpty()) {
                            Object instance = getBeanByQualifier(field.getType(), qualifierValue);
                            if (instance != null) {
                                field.setAccessible(true);
                                field.set(serviceInstance, instance);
                            }
                        } else {
                            Object instance = getBean(field.getType());
                            if (instance != null) {
                                field.setAccessible(true);
                                field.set(serviceInstance, instance);
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Object getBean(Class<?> interfaceClass) {
        try {
            for (Object theClass : beans.values()) {
                Class<?>[] interfaces = theClass.getClass().getInterfaces();
                for (Class<?> theInterface : interfaces) {
                    if (theInterface == interfaceClass)
                        return theClass;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Object getBean(String beanName) {
        return beans.get(beanName);
    }

    private Object getBeanByQualifier(Class<?> type, String qualifierValue) {
        for (Object bean : beans.values()) {
            if (type.isInstance(bean)) {
                Qualifier qualifier = bean.getClass().getAnnotation(Qualifier.class);
                if (qualifier != null && qualifier.value().equals(qualifierValue)) {
                    return bean;
                }
            }
        }
        return null;
    }
}
