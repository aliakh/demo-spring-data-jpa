package demo;

import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;

import java.lang.reflect.Method;

public class DebugAsyncUncaughtExceptionHandler implements AsyncUncaughtExceptionHandler {

    @Override
    public void handleUncaughtException(Throwable throwable, Method method, Object... params) {
        System.out.println("Exception message: " + throwable.getMessage());
        System.out.println("Method name: " + method.getName());
        for (Object param : params) {
            System.out.println("Parameter value: " + param);
        }
    }
}
