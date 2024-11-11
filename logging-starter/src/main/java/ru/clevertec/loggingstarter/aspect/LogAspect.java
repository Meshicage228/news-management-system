package ru.clevertec.loggingstarter.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import ru.clevertec.loggingstarter.annotation.LogRequestResponse;

/**
 * Аспект для логирования входящих и исходящих данных методов,
 * аннотированных {@link LogRequestResponse}.
 */
@Aspect
@Slf4j
public class LogAspect {

    /**
     * Точка среза для методов и классов, аннотированных {@link LogRequestResponse}.
     */
    @Pointcut("within(@ru.clevertec.loggingstarter.annotation.LogRequestResponse *) && execution(* *(..))")
    public void annotatedByLoggableWithDuration() {}

    /**
     * Логирует входящие и исходящие данные методов, соответствующих точке среза.
     *
     * @param joinPoint информация о вызванном методе
     * @return результат выполнения метода
     * @throws Throwable исключения, возникающие при выполнении метода
     */
    @Around("annotatedByLoggableWithDuration()")
    public Object logPointcut(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("Entering method {} with arguments: {}", joinPoint.getSignature().getName(), joinPoint.getArgs());

        Object result;
        try {
            result = joinPoint.proceed();
            log.info("Exiting method {} with response: {}", joinPoint.getSignature().getName(), result);
        } catch (Throwable throwable) {
            log.error("Exception in method {} with message: {}", joinPoint.getSignature().getName(), throwable.getMessage(), throwable);
            throw throwable;
        }

        return result;
    }
}
