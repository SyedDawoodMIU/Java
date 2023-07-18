package org.project;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

class BeforeProxy implements InvocationHandler {
    private Object target;
    private Method beforeMethod;
    private Method methodToProxify;
    private Object aspect;

    public BeforeProxy(Object target, Object aspect, Method methodToProxify, Method beforeMethod) {
        this.target = target;
        this.beforeMethod = beforeMethod;
        this.methodToProxify = methodToProxify;
        this.aspect = aspect;

    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object result = null;
        if (method.getName().equals(methodToProxify.getName())) {
            beforeMethod.invoke(aspect, args);
            result = method.invoke(target, args);
        } else {
            result = method.invoke(target, args);
        }
        return result;
    }
}
