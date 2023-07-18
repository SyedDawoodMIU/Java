package org.project;

import org.project.annotations.Async;
import org.project.annotations.Autowired;
import org.project.annotations.EventListner;
import org.project.annotations.Profile;
import org.project.annotations.Scheduled;
import org.project.services.IService2;

@org.project.annotations.Service
@Profile({ "dev", "prod" })

public class Main implements Runnable {

    @Autowired
    private IService2 myService2;

    public static void main(String[] args) {
        MySpringFramework.run(Main.class, args);

    }

    @EventListner
    public void onEvent(MyEvent event) {
        System.out.println("Event" + event.getMessage());
    }

    @EventListner
    public void onNewEvent(MyEvent event) {
        System.out.println("New " + event.getMessage());
    }

    @Override
    @Scheduled(fixedRate = 2)
    @Async
    // @Scheduled(fixedRate = 2)
    public void run() {
        System.out.println(myService2.getHello());
        MySpringFramework.publishEvent(new MyEvent("Hello World!"));
    }
}