package aspects;

import java.time.LocalDateTime;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import customers.EmailSender;

@Aspect
@Component
public class EmailAdvice {

    @After("execution(* customers.EmailSender.sendEmail(..))")
    public void afterSendEmail(JoinPoint joinPoint) {
        System.out.println(LocalDateTime.now() + " method=sentEmail");
    }

    @After("execution(* customers.EmailSender.sendEmail(String, String)) && args(message, emailAddress)")
    public void aroundSendEmail(JoinPoint joinPoint, String message, String emailAddress) throws Throwable {
        System.out.println(LocalDateTime.now() + " method=sentEmail address=" + emailAddress + " message=" + message);
    }

    @After("execution(* customers.EmailSender.sendEmail(String, String)) && args(message, emailAddress)")
    public void afterSendEmail(JoinPoint joinPoint, String message, String emailAddress) {
        EmailSender emailSender = (EmailSender) joinPoint.getTarget();
        String outgoingMailServer = emailSender.getOutgoingMailServer();
        System.out.println(LocalDateTime.now() + " method=sentEmail address=" + emailAddress +
                " message=" + message + " outgoing mail server=" + outgoingMailServer);
    }

    @Around("execution(* customers.CustomerDAO.save(..))")
    public Object measureMethodExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        Object result = joinPoint.proceed();

        stopWatch.stop();
        long executionTime = stopWatch.getLastTaskTimeMillis();

        System.out.println("Method " + joinPoint.getSignature().getName() +
                " execution time: " + executionTime + "ms");

        return result;
    }

}
