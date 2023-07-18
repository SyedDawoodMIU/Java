package org.example;

import org.project.annotations.Service;

@Service
public class AspectTestClass implements INewAspectTestClass {
    @Override
    public void getMessage() {
        System.out.println("Hello from new aspect");
    }
}
