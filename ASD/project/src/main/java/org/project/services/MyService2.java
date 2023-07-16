package org.project.services;

import org.project.annotations.Autowired;
import org.project.annotations.Service;

@Service
public class MyService2 implements IService2 {

    private IService myService;

    @Autowired
    public MyService2(IService myService) {
        this.myService = myService;
        System.out.println("MyService2 constructor");
    }

    public String getHello() {
        return myService.getHello();
    }

}
