package org.project;

import org.project.annotations.After;
import org.project.annotations.Around;
import org.project.annotations.Async;
import org.project.annotations.Autowired;
import org.project.annotations.Before;
import org.project.annotations.EventListner;
import org.project.annotations.Profile;
import org.project.annotations.Scheduled;
import org.project.services.IService2;

@org.project.annotations.Service
@Profile({ "dev", "prod" })

public class Main implements Runnable {

    @Autowired
    private IService2 myService2;

    @Autowired
    private INewAspectTestClass aspecTest;

    public static void main(String[] args) {
        MySpringFramework.run(Main.class, args);

    }

    @EventListner
    public void onEvent(MyEvent event) {
        System.out.println("Event" + event.getMessage());
        aspecTest.getMessage();
    }

    @EventListner
    public void onNewEvent(MyEvent event) {
        System.out.println("New " + event.getMessage());
    }

    @Override
    // @Scheduled(fixedRate = 2)
    // @Async
    // @Scheduled(fixedRate = 2)
    public void run() {
        System.out.println(myService2.getHello());
        MySpringFramework.publishEvent(new MyEvent("Hello World!"));

    }

    @Before(pointcut = "NewAspectTestClass.getMessage")
    public void beforeRun() {

        System.out.println("Before getMessage");
    }

    @After(pointcut = "NewAspectTestClass.getMessage")
    public void afterRun() {
        System.out.println("After getMessage");
    }

    @Around(pointcut = "NewAspectTestClass.getMessage")
    public void AroundRun() {
        System.out.println("Around getMessage");
    }
}
