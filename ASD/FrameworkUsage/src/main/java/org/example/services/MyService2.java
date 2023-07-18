package org.example.services;

import org.project.annotations.Autowired;
import org.project.annotations.Qualifier;
import org.project.annotations.Service;
import org.project.annotations.Value;

@Service
public class MyService2 implements IService2 {

    @Value("MyService2")
    private String name;

    @Autowired
    private IService myService;

    @Autowired
    @Qualifier("org.project.services.MyServiceNew")
    public void setService(IService service) {
        this.myService = service;
    }

    public MyService2() {
        System.out.println("MyService2 constructor");
    }

    public String getHello() {
        System.out.println("MyService2: " + name);
        return myService.getHello();

    }

}
