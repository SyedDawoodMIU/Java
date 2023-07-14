package schedulars;

import java.time.LocalDateTime;

import org.springframework.scheduling.annotation.Scheduled;

public class LoggerScheduler {

    @Scheduled(fixedRate = 15000)
    public void logCurrentTime() {
        System.out.println("Current time: " + LocalDateTime.now());
    }
}
