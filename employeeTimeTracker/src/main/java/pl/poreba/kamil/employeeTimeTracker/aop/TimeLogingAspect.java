package pl.poreba.kamil.employeeTimeTracker.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@EnableAspectJAutoProxy
@Component
public class TimeLogingAspect {

    @Around("@annotation(timeDuration)")
    public Object logDurationTime(ProceedingJoinPoint joinPoint, TimeDuration timeDuration) throws Throwable {
        long start = System.nanoTime();
        Object proceed = joinPoint.proceed();
        log.info(joinPoint.getSignature().getName() +" :: Time :: " + (System.nanoTime() - start) + "ns");
        return proceed;
    }
}
