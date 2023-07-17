package org.project;

import org.project.annotations.Autowired;
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

    @Override
    @Scheduled(cron = "0 15 10 ? * *")
    // @Scheduled(fixedRate = 2)
    public void run() {
        System.out.println(myService2.getHello());
    }
}