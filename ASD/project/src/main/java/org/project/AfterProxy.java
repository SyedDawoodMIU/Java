package org.project;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

class AfterProxy implements InvocationHandler {
    private Object target;
    private Method afterMethod;
    private Method methodToProxify;
    private Object aspect;

    public AfterProxy(Object target, Object aspect, Method methodToProxify, Method beforeMethod) {
        this.target = target;
        this.afterMethod = beforeMethod;
        this.methodToProxify = methodToProxify;
        this.aspect = aspect;

    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object result = null;
        if (method.getName().equals(methodToProxify.getName())) {
            result = method.invoke(target, args);
            afterMethod.invoke(aspect, args);
        } else {
            result = method.invoke(target, args);
        }
        return result;
    }
}
