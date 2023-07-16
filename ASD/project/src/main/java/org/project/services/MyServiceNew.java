package org.project.services;

import org.project.annotations.Service;

@Service
public class MyServiceNew implements IService {
    
    public String getHello() {
        return "Hello from MyServiceNew";
    }

}