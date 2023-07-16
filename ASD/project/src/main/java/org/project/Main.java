package org.project;

import org.project.annotations.Autowired;
import org.project.annotations.Service;
import org.project.services.MyService2;
import org.reflections.Reflections;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Main {
    private static List<Object> serviceObjectMap = new ArrayList<>();

    public static void main(String[] args) {

        try {
            MySpringFramework framework = new MySpringFramework();
            framework.scan("org.project");
            // framework.performDI();

            MyService2 myService2 = (MyService2) framework.getBean(MyService2.class);
            System.out.println(myService2.getHello());
        } catch (Exception e) {
           


        }

    }
}