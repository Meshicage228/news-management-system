package ru.clevertec.loggingstarter.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Slf4j
@Component
public class LogAspect {
    @Pointcut("within(@ru.clevertec.loggingstarter.annotation.LogRequestResponse *) && execution(* *(..))")
    public void annotatedByLoggableWithDuration() {}

    @Around("annotatedByLoggableWithDuration()")
    public Object logPointcut(ProceedingJoinPoint joinPoint) {
        log.info("Entering method {} with arguments : {}", joinPoint.getSignature().getName(), joinPoint.getArgs());
        // todo exception
        Object proceed = null;
        try {
            proceed = joinPoint.proceed();
            log.info("Exiting method {} with response : {}", joinPoint.getSignature().getName(), proceed);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }

        return proceed;
    }
}
