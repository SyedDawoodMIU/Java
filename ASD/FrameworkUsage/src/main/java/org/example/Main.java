package org.example;

import org.project.MySpringFramework;
import org.project.annotations.*;
import org.example.services.IService2;

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
     @Scheduled(fixedRate = 2)
     @Async
    // @Scheduled(cron = "*/2 * * * * *")
    public void run() {
        System.out.println(myService2.getHello());
        MySpringFramework.publishEvent(new MyEvent("Hello World!"));

    }

    @Before(pointcut = "AspectTestClass.getMessage")
    public void beforeRun() {

        System.out.println("Before getMessage");
    }

    @After(pointcut = "AspectTestClass.getMessage")
    public void afterRun() {
        System.out.println("After getMessage");
    }

    @Around(pointcut = "AspectTestClass.getMessage")
    public void AroundRun() {
        System.out.println("Around getMessage");
    }
}
