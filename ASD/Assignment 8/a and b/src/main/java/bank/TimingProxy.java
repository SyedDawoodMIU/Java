package bank;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class TimingProxy implements InvocationHandler {
    private Object target;

    public TimingProxy(Object target) {
        this.target = target;
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object result = method.invoke(target, args);
        long endTime = System.currentTimeMillis();
        System.out.println("Method " + method.getName() + " took " + (endTime - startTime) + " ms");
        return result;
    }
}
