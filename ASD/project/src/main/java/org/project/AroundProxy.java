package org.project;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

class AroundProxy implements InvocationHandler {
    private Object target;
    private Method aroundMethod;
    private Method methodToProxify;
    private Object aspect;

    public AroundProxy(Object target, Object aspect, Method methodToProxify, Method beforeMethod) {
        this.target = target;
        this.aroundMethod = beforeMethod;
        this.methodToProxify = methodToProxify;
        this.aspect = aspect;

    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object result = null;
        if (method.getName().equals(methodToProxify.getName())) {
            aroundMethod.invoke(aspect, args);
            result = method.invoke(target, args);
            aroundMethod.invoke(aspect, args);

        } else {
            result = method.invoke(target, args);
        }
        return result;
    }
}
