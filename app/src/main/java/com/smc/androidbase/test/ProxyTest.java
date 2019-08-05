package com.smc.androidbase.test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 动态代理
 * @author shenmengchao
 * @version 1.0.0
 */
public class ProxyTest {

    public static void main(String[] args) {
        RealSubject realSubject = new RealSubject();
        ProxyHandler proxyHandler = new ProxyHandler(realSubject);
        Subject subject = (Subject) Proxy.newProxyInstance(
                RealSubject.class.getClassLoader(),
                RealSubject.class.getInterfaces(),
                proxyHandler);
        subject.request();
    }

}

interface Subject {
    void request();
}

class RealSubject implements Subject {

    @Override
    public void request() {
        System.out.println("-RealSubject request-");
    }
}

class ProxyHandler implements InvocationHandler {

    private Subject subject;

    public ProxyHandler(Subject subject) {
        this.subject = subject;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("-ProxyHandler invoke start-");
        Object result = method.invoke(subject, args);
        System.out.println("-ProxyHandler invoke end-");
        return result;
    }
}

