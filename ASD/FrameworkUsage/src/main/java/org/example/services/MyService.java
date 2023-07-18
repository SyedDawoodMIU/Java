package org.example.services;

import org.project.annotations.Service;

@Service
public class MyService implements IService {
    
    public String getHello() {
        return "Hello";
    }

}

