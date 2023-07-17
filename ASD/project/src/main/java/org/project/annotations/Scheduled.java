package org.project.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Scheduled {
    String cron() default "";

    long fixedRate() default -1;

    long initialDelay() default -1;

    TimeUnit timeUnit() default TimeUnit.SECONDS;
}