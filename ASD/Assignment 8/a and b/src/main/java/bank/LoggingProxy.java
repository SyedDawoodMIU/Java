package bank;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;


public class LoggingProxy implements InvocationHandler {
    private Object target;

    public LoggingProxy(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object arg0, Method arg1, Object[] arg2) throws Throwable {

        System.out.println("Method " + arg1.getName() + " is called");
        var result = arg1.invoke(target, arg2);
        System.out.println("Method " + arg1.getName() + " is finished");
        return result;
    }

}
