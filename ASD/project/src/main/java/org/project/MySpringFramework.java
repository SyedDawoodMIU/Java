package org.project;

import java.time.Duration;
import java.time.ZonedDateTime;
import java.time.temporal.Temporal;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.cronutils.model.Cron;
import com.cronutils.model.CronType;
import com.cronutils.model.definition.CronDefinitionBuilder;
import com.cronutils.model.time.ExecutionTime;
import com.cronutils.model.time.generator.*;
import com.cronutils.parser.CronParser;

import java.beans.EventHandler;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ScheduledExecutorService;

import org.project.annotations.Autowired;
import org.project.annotations.Profile;
import org.project.annotations.Qualifier;
import org.project.annotations.Scheduled;
import org.project.annotations.Service;
import org.project.annotations.Value;
import org.reflections.Reflections;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MySpringFramework {
    private Map<String, Object> beans = new HashMap<>();
    private Properties properties = new Properties();
    private String[] activeProfiles;
    private ScheduledExecutorService scheduledExecutor;
    private static Map<Class<?>, List<EventHandlerWrapper>> eventListners = new HashMap<>();

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
        scheduledExecutor = Executors.newSingleThreadScheduledExecutor();
        loadProperties(primarySource);

        Set<Class<?>> serviceTypes = reflections.getTypesAnnotatedWith(Service.class);
        for (Class<?> serviceClass : serviceTypes) {
            Profile profileAnnotation = serviceClass.getAnnotation(Profile.class);
            if (profileAnnotation != null) {
                String[] profileValues = profileAnnotation.value();
                if (shouldInstantiateBean(profileValues)) {
                    instantiateBean(serviceClass);
                }
            } else {
                instantiateBean(serviceClass);
            }

        }

        for (Class<?> serviceClass : serviceTypes) {
            performConstructorInjection(serviceClass);
            performSetterInjection(serviceClass);
        }
        performFieldInjection();
        registerEventListner();
        scheduleTasks();

    }

    private void instantiateBean(Class<?> serviceClass)
            throws InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        Constructor<?>[] constructors = serviceClass.getConstructors();

        if (constructors.length == 1 && constructors[0].getParameterCount() == 0) {
            Object instance = serviceClass.getDeclaredConstructor().newInstance();
            beans.put(serviceClass.getName(), instance);
        }
    }

    private void loadProperties(Class<?> primarySource) throws IOException {
        try (InputStream input = primarySource.getClassLoader().getResourceAsStream("application.properties")) {
            if (input == null) {
                throw new FileNotFoundException("application.properties file not found");
            }
            properties.load(input);
            activeProfiles = loadActiveProfiles(properties);
        } catch (IOException e) {
            throw new IOException("Error loading application.properties file: " + e.getMessage());
        }
    }

    private String[] loadActiveProfiles(Properties properties) {
        String activeProfiles = properties.getProperty("spring.profiles.active");
        if (activeProfiles != null && !activeProfiles.isEmpty()) {
            return activeProfiles.split(",");
        }
        return new String[0];
    }

    private boolean shouldInstantiateBean(String[] profileValues) {
        if (profileValues.length == 0) {
            return true; // If no specific profiles are specified, instantiate the bean for all profiles
        }

        for (String profile : activeProfiles) {
            if (Arrays.asList(profileValues).contains(profile)) {
                return true; // If the bean's profile matches the active profile, instantiate the bean
            }
        }

        return false;
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

    private void scheduleTasks() {
        for (Object bean : beans.values()) {
            Class<?> beanClass = bean.getClass();
            Method[] methods = beanClass.getDeclaredMethods();
            for (Method method : methods) {
                if (method.isAnnotationPresent(Scheduled.class)) {
                    Scheduled scheduledAnnotation = method.getAnnotation(Scheduled.class);
                    String cronExpression = scheduledAnnotation.cron();
                    if (!cronExpression.isEmpty()) {
                        scheduledExecutor.scheduleAtFixedRate(() -> {
                            try {
                                method.invoke(bean);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }, getNextDelay(cronExpression), getInterval(cronExpression), TimeUnit.SECONDS);

                    } else {
                        long initialDelay = scheduledAnnotation.initialDelay();
                        long fixedRate = scheduledAnnotation.fixedRate();
                        TimeUnit timeUnit = scheduledAnnotation.timeUnit();
                        scheduledExecutor.scheduleAtFixedRate(() -> {
                            try {
                                method.invoke(bean);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }, initialDelay, fixedRate, timeUnit);

                    }

                }
            }
        }
    }

    private long getNextDelay(String cronExpression) {
        CronParser parser = new CronParser(CronDefinitionBuilder.instanceDefinitionFor(CronType.QUARTZ));
        Cron cron = parser.parse(cronExpression);
        ZonedDateTime now = ZonedDateTime.now();
        Optional<ZonedDateTime> nextExecutionOptional = ExecutionTime.forCron(cron).nextExecution(now);
        ZonedDateTime nextExecution = nextExecutionOptional.orElse(null);
        Duration duration = Duration.between(now, nextExecution);
        return duration.getSeconds();
    }

    private long getInterval(String cronExpression) {
        CronParser parser = new CronParser(CronDefinitionBuilder.instanceDefinitionFor(CronType.QUARTZ));
        Cron cron = parser.parse(cronExpression);
        List<ZonedDateTime> executionTimes = ExecutionTime.forCron(cron).getExecutionDates(ZonedDateTime.now(),
                ZonedDateTime.now().plusYears(1));
        if (executionTimes.size() > 1) {
            Duration duration = Duration.between(executionTimes.get(0), executionTimes.get(1));
            return duration.getSeconds();
        }
        return 0;
    }

    public static void publishEvent(Object event) {
        List<EventHandlerWrapper> handlers = eventListners.getOrDefault(event.getClass(), Collections.emptyList());
        for (EventHandlerWrapper handlerWrapper : handlers) {
            try {
                handlerWrapper.invoke(event);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void registerEventListner() throws InstantiationException, IllegalAccessException, IllegalArgumentException,
            InvocationTargetException, NoSuchMethodException, SecurityException {
        for (Object bean : beans.values()) {
            Class<?> beanClass = bean.getClass();
            Method[] methods = beanClass.getDeclaredMethods();
            for (Method method : methods) {
                if (method.isAnnotationPresent(org.project.annotations.EventListner.class)) {
                    Class<?>[] parameterTypes = method.getParameterTypes();
                    if (parameterTypes.length == 1) {
                        Class<?> eventType = parameterTypes[0];
                        if (!eventListners.containsKey(eventType)) {
                            eventListners.put(eventType, Arrays.asList(new EventHandlerWrapper(bean, method)));

                        } else {
                            List<EventHandlerWrapper> handlers = eventListners.get(eventType);
                            handlers.add(new EventHandlerWrapper(bean, method));
                            eventListners.put(eventType, handlers);
                        }
                    }
                }
            }
        }
    }

    private static class EventHandlerWrapper {
        private final Object bean;
        private final Method method;

        public EventHandlerWrapper(Object bean, Method method) {
            this.bean = bean;
            this.method = method;
        }

        public void invoke(Object event) throws IllegalAccessException, InvocationTargetException {
            method.invoke(bean, event);
        }
    }

}
