package org.project;

import org.project.annotations.Service;

@Service
public class MyEvent {
    private String message;

    public MyEvent(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

}


