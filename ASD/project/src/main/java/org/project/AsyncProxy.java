package org.project;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.CompletableFuture;

class AsyncProxy implements InvocationHandler {
    private Object target;
    private Method methodToProxify;

    public AsyncProxy(Object target, Method methodToProxify) {
        this.target = target;
        this.methodToProxify = methodToProxify;

    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object result = null;

        CompletableFuture.runAsync(() -> {
            try {
                method.invoke(target, args);
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        });
        return result;
    }
}
