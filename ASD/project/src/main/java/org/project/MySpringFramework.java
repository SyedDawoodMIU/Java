package org.project;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.project.annotations.Autowired;
import org.project.annotations.Service;
import org.reflections.Reflections;

public class MySpringFramework {
    private Map<String, Object> beans = new HashMap<>();

   public void scan(String basePackage) throws Exception {
    Reflections reflections = new Reflections(basePackage);

    Set<Class<?>> serviceTypes = reflections.getTypesAnnotatedWith(Service.class);
    for (Class<?> serviceClass : serviceTypes) {
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
    performDI();
}


    public void performDI() {
        try {
            for (Object serviceClass : beans.values()) {
                // find annotated fields
                for (Field field : serviceClass.getClass().getDeclaredFields()) {
                    if (field.isAnnotationPresent(Autowired.class)) {
                        // get the type of the field
                        Class<?> theFieldType = field.getType();
                        // get the object instance of this type
                        Object instance = getBean(theFieldType);
                        // do the injection
                        field.setAccessible(true);
                        field.set(serviceClass, instance);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Object getBean(Class interfaceClass) {
        try {
            for (Object theClass : beans.values()) {
                Class<?>[] interfaces = theClass.getClass().getInterfaces();
                for (Class<?> theInterface : interfaces) {
                    if (theInterface.getName().contentEquals(interfaceClass.getName()))
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

}
