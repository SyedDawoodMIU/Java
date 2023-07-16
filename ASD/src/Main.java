import java.util.Set;

import annotations.Autowired;
import annotations.Service;

public class Main {
    public static void main(String[] args) {
        Reflections reflections = new Reflections("");
        Set<Class<?>> serviceClasses = reflections.getTypesAnnotatedWith(Service.class);
        for (Class<?> serviceClass : serviceClasses) {
            Object serviceObject = serviceClass.newInstance();
            Field[] fields = serviceClass.getDeclaredFields();
            for (Field field : fields) {
                if (field.isAnnotationPresent(Autowired.class)) {
                    Object fieldObject = reflections.getBean(field.getType());
                    field.setAccessible(true);
                    field.set(serviceObject, fieldObject);
                }
            }
        }
    }
}