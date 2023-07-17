package org.project;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import org.project.annotations.Autowired;
import org.project.annotations.Qualifier;
import org.project.annotations.Service;
import org.project.annotations.Value;
import org.reflections.Reflections;

public class MySpringFramework {
    private Map<String, Object> beans = new HashMap<>();
    private Properties properties = new Properties();

    public static void run(Class<?> primarySource, String... args) {
        try {
            MySpringFramework framework = new MySpringFramework();
            framework.scan(primarySource);
            Object mainClassInstance = framework.beans.get(primarySource.getName());
            mainClassInstance.getClass().getDeclaredMethod("run").invoke(mainClassInstance);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void scan(Class<?> primarySource) throws Exception {
        Reflections reflections = new Reflections(primarySource.getPackage().getName());
        loadProperties(primarySource);

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

    private void loadProperties(Class<?> primarySource) throws IOException {
        try (InputStream input = primarySource.getClassLoader().getResourceAsStream("application.properties")) {
            if (input == null) {
                throw new FileNotFoundException("application.properties file not found");
            }
            properties.load(input);
        } catch (IOException e) {
            throw new IOException("Error loading application.properties file: " + e.getMessage());
        }
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

                    Qualifier qualifierAnnotation = method.getAnnotation(Qualifier.class);
                    String qualifierValue = qualifierAnnotation.value();
                    Object instance;
                    if (!qualifierValue.isEmpty()) {
                        instance = getBeanByQualifier(parameterTypes[0], qualifierValue);
                        if (instance != null) {
                            method.invoke(beans.get(serviceClass.getName()), instance);
                        }
                    } else {

                        instance = getBean(parameterTypes[0]);
                        if (instance != null) {
                            method.invoke(beans.get(serviceClass.getName()), instance);
                        }
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

                        Object instance;
                        if (field.isAnnotationPresent(Qualifier.class)) {
                            Qualifier qualifierAnnotation = field.getAnnotation(Qualifier.class);
                            String qualifierValue = qualifierAnnotation.value();

                            if (!qualifierValue.isEmpty()) {
                                instance = getBeanByQualifier(field.getType(), qualifierValue);
                                if (instance != null) {
                                    field.setAccessible(true);
                                    field.set(serviceInstance, instance);
                                }
                            }
                        } else {
                            instance = getBean(field.getType());
                            if (instance != null) {
                                field.setAccessible(true);
                                field.set(serviceInstance, instance);
                            }
                        }

                    }

                    if (field.isAnnotationPresent(Value.class)) {
                        Value valueAnnotation = field.getAnnotation(Value.class);
                        String propertyKey = valueAnnotation.value();
                        String propertyValue = properties.getProperty(propertyKey);
                        if (propertyValue != null) {
                            field.setAccessible(true);
                            Object instance = field.getType().getConstructor(String.class).newInstance(propertyValue);
                            field.set(serviceInstance, instance);

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
        try {
            for (Object theClass : beans.values()) {
                Class<?>[] interfaces = theClass.getClass().getInterfaces();
                for (Class<?> theInterface : interfaces) {
                    if (theInterface == type && theClass.getClass().getName().equals(qualifierValue))
                        return theClass;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
