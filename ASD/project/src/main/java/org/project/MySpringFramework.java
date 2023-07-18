package org.project;

import java.time.Duration;
import java.time.ZonedDateTime;

import com.cronutils.model.Cron;
import com.cronutils.model.CronType;
import com.cronutils.model.definition.CronDefinitionBuilder;
import com.cronutils.model.time.ExecutionTime;
import com.cronutils.parser.CronParser;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

import org.project.annotations.*;
import org.reflections.Reflections;

public class MySpringFramework {
    private Map<String, Object> beans = new HashMap<>();
    private Properties properties = new Properties();
    private String[] activeProfiles;
    private ScheduledExecutorService scheduledExecutor;
    private static Map<Class<?>, List<EventHandlerWrapper>> eventListners = new HashMap<>();

    /**
     * This method is used to run the Spring Framework application.
     * It creates an instance of the MySpringFramework class, scans for annotated
     * classes,
     * instantiates beans, performs dependency injection, and schedules tasks using
     * the Cron expression syntax.
     * 
     * The method then retrieves the main class instance from the beans map, and
     * invokes its "run" method, if it exists.
     * 
     * @param primarySource the main class of the Spring Framework application
     * @param args          command line arguments
     */
    public static void run(Class<?> primarySource, String... args) {
        try {
            MySpringFramework framework = new MySpringFramework();
            framework.scan(primarySource);
            Object mainClassInstance = framework.beans.get(primarySource.getName());

            if (mainClassInstance.getClass().getDeclaredMethod("run") != null) {
                mainClassInstance.getClass().getDeclaredMethod("run").invoke(mainClassInstance);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * This class represents a simple Spring Framework implementation.
     * It provides methods to scan for annotated classes, instantiate beans, perform
     * dependency injection, and schedule tasks using the Cron expression syntax.
     * 
     * The class uses the Reflections library to scan for classes annotated with
     * the @Service annotation.
     * It then instantiates beans for these classes using their no-argument
     * constructors, and performs dependency injection
     * using constructor injection, setter injection, and field injection.
     * 
     * The class also supports the @Profile annotation, which allows for conditional
     * bean instantiation based on the active profiles
     * specified in the "application.properties" file.
     * 
     * Finally, the class provides a method to schedule tasks using the Cron
     * expression syntax, using the CronUtils library.
     * 
     * @see <a href="https://github.com/ronmamo/reflections">Reflections library</a>
     * @see <a href="https://github.com/jmrozanec/cron-utils">CronUtils library</a>
     */
    private void scan(Class<?> primarySource) throws Exception {
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

    /**
     * Instantiates a bean for the given service class if it has a no-argument
     * constructor.
     * The instantiated bean is then added to the map of beans with the service
     * class name as the key.
     *
     * @param serviceClass the service class to instantiate a bean for
     */
    private void instantiateBean(Class<?> serviceClass)
            throws InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        Constructor<?>[] constructors = serviceClass.getConstructors();

        if (constructors.length == 1 && constructors[0].getParameterCount() == 0) {
            Object instance = serviceClass.getDeclaredConstructor().newInstance();
            beans.put(serviceClass.getName(), instance);
        }
    }

    /**
     * Loads the application properties from the "application.properties" file
     * located in the classpath of the primary source.
     * If the file is not found, a FileNotFoundException is thrown.
     * The active profiles are also loaded from the properties file using the
     * "spring.profiles.active" property.
     *
     * @param primarySource the primary source class used to locate the
     */

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

    /**
     * Loads the active profiles from the application.properties file.
     * If the "spring.profiles.active" property is not specified, an empty array is
     * returned.
     *
     * @param properties the Properties object containing the application properties
     * @return an array of active profiles, or an empty array if no profiles are
     */
    private String[] loadActiveProfiles(Properties properties) {
        String activeProfiles = properties.getProperty("spring.profiles.active");
        if (activeProfiles != null && !activeProfiles.isEmpty()) {
            return activeProfiles.split(",");
        }
        return new String[0];
    }

    /**
     * Determines whether a bean should be instantiated based on its profile
     * annotation and the active profiles.
     * If no specific profiles are specified, the bean will be instantiated for all
     * profiles.
     * If the bean's profile matches the active profile, the bean will be
     * instantiated.
     *
     * @param profileValues the profile values specified in the bean's profile
     * @return true if the bean should be instantiated, false otherwise
     */

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

    /**
     * Performs constructor injection for all beans in the container.
     * If a constructor is annotated with @Autowired, the framework will attempt to
     * inject an instance of the required type.
     * If a constructor has multiple parameters, the framework will attempt to
     * inject instances of the required types in the order they appear.
     *
     * @param serviceClass the class to perform constructor injection on
     * @throws InvocationTargetException
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     * @throws InstantiationException
     */

    private void performConstructorInjection(Class<?> serviceClass)
            throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
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

    /**
     * Performs setter injection for all beans in the container.
     * If a method is annotated with @Autowired, the framework will attempt to
     * inject an instance of the required type.
     * If a method is annotated with @Qualifier, the framework will attempt to
     * inject an instance of the required type with the specified qualifier value.
     * The method must have only one parameter.
     *
     * @param serviceClass the class to perform setter injection on
     */

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

    /**
     * Performs field injection for all beans in the container.
     * If a field is annotated with @Autowired, the framework will attempt to inject
     * an instance of the required type.
     * If a field is annotated with @Qualifier, the framework will attempt to inject
     * an instance of the required type with the specified qualifier value.
     * If a field is annotated with @Value, the framework will attempt to inject a
     * value from the properties file with the specified key.
     */
    private void performFieldInjection() {
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

    /**
     * Returns an instance of a bean that implements the specified interface.
     * 
     * @param interfaceClass the interface class of the bean
     * @return an instance of the bean that implements the specified interface, or
     */
    private Object getBean(Class<?> interfaceClass) {
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

    /**
     * Returns the bean instance with the specified name.
     *
     * @param beanName the name of the bean to retrieve
     * @return the bean instance with the specified name, or null if not found
     */
    private Object getBean(String beanName) {
        return beans.get(beanName);
    }

    /**
     * Returns an instance of a bean by its qualifier value.
     * 
     * @param type           the class type of the bean
     * @param qualifierValue the qualifier value of the bean
     * @return an instance of the bean with the specified qualifier value, or null
     */
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

    /**
     * Schedules tasks based on the @Scheduled annotation present on methods of
     * beans.
     * If the method has a cron expression specified, the task is scheduled to run
     * at the specified intervals.
     * If the method has an initial delay, fixed rate, and time unit specified, the
     * task is scheduled to run at fixed intervals.
     */
    private void scheduleTasks() {
        for (Object bean : beans.values()) {
            Class<?> beanClass = bean.getClass();
            Method[] methods = beanClass.getDeclaredMethods();
            for (Method method : methods) {
                if (method.isAnnotationPresent(Scheduled.class)) {
                    Scheduled scheduledAnnotation = method.getAnnotation(Scheduled.class);
                    String cronExpression = scheduledAnnotation.cron();
                    if (!cronExpression.isEmpty()) {
                        scheduledExecutor.scheduleAtFixedRate(() -> invokeMethod(bean, method),
                                getNextDelay(cronExpression), getInterval(cronExpression), TimeUnit.SECONDS);
                    } else {
                        long initialDelay = scheduledAnnotation.initialDelay();
                        long fixedRate = scheduledAnnotation.fixedRate();
                        TimeUnit timeUnit = scheduledAnnotation.timeUnit();
                        scheduledExecutor.scheduleAtFixedRate(() -> invokeMethod(bean, method),
                                initialDelay, fixedRate, timeUnit);
                    }

                }
            }
        }
    }

    /**
     * Invokes the given method.
     *
     * @param bean   the bean instance
     * @param method the method to invoke
     */
    private void invokeMethod(Object bean, Method method) {
        if (method.isAnnotationPresent(Async.class)) {
            CompletableFuture.runAsync(() -> {
                try {
                    method.invoke(bean);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            });

        } else {
            try {
                method.invoke(bean);
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * Calculates the delay until the next execution of a scheduled task based on
     * the provided cron expression.
     * Parses the cron expression using the Quartz cron definition.
     * Calculates the next execution time of the cron expression after the current
     * time.
     * Calculates the duration between the current time and the next execution time
     * and returns the duration in seconds.
     * 
     * @param cronExpression the cron expression used to schedule the task.
     * @return the delay until the next execution of the scheduled task in seconds.
     */
    private long getNextDelay(String cronExpression) {
        CronParser parser = new CronParser(CronDefinitionBuilder.instanceDefinitionFor(CronType.QUARTZ));
        Cron cron = parser.parse(cronExpression);
        ZonedDateTime now = ZonedDateTime.now();
        Optional<ZonedDateTime> nextExecutionOptional = ExecutionTime.forCron(cron).nextExecution(now);
        ZonedDateTime nextExecution = nextExecutionOptional.orElse(null);
        Duration duration = Duration.between(now, nextExecution);
        return duration.getSeconds();
    }

    /**
     * Calculates the interval between two consecutive executions of a scheduled
     * task based on the provided cron expression.
     * Parses the cron expression using the Quartz cron definition.
     * Calculates the next two execution times of the cron expression within the
     * next year.
     * Calculates the duration between the first two execution times and returns the
     * duration in seconds.
     * 
     * @param cronExpression the cron expression used to schedule the task.
     * @return the interval between two consecutive executions of the scheduled task
     */
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

    /**
     * Publishes an event to all registered event listeners for that event type.
     * Retrieves the list of event handlers for the given event type from the event
     * listeners map.
     * Invokes each event handler with the given event object.
     * 
     * @param event the event object to be published.
     */
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

    /**
     * Registers event listeners by scanning all beans for methods annotated with
     * {@link EventListner}.
     * The method should have only one parameter, which is the event type.
     * If the event type is not registered, a new list of event handlers is created
     * and added to the event listeners map.
     * If the event type is already registered, the new event handler is added to
     * the existing list of event handlers.
     * 
     */
    private void registerEventListner() throws InstantiationException, IllegalAccessException, IllegalArgumentException,
            InvocationTargetException, NoSuchMethodException, SecurityException {
        for (Object bean : beans.values()) {
            Class<?> beanClass = bean.getClass();
            Method[] methods = beanClass.getDeclaredMethods();
            for (Method method : methods) {
                if (method.isAnnotationPresent(EventListner.class)) {
                    Class<?>[] parameterTypes = method.getParameterTypes();
                    if (parameterTypes.length == 1) {
                        Class<?> eventType = parameterTypes[0];
                        eventListners.computeIfAbsent(eventType, k -> new ArrayList<EventHandlerWrapper>())
                                .add(new EventHandlerWrapper(bean, method));
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
