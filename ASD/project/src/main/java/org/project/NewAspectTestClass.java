package org.project;

import org.project.annotations.Service;

@Service
public class NewAspectTestClass implements INewAspectTestClass {
    private String message;

    @Override
    public String getMessage() {
        return message;
    }

}