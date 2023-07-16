package org.project;

import org.project.services.IService2;
import org.project.services.MyService2;
import java.util.ArrayList;
import java.util.List;

public class Main {
    private static List<Object> serviceObjectMap = new ArrayList<>();

    public static void main(String[] args) {

        try {
            MySpringFramework framework = new MySpringFramework();
            framework.scan("org.project");

            MyService2 myService2 = (MyService2) framework.getBean(IService2.class);
            System.out.println(myService2.getHello());
        } catch (Exception e) {

        }

    }
}