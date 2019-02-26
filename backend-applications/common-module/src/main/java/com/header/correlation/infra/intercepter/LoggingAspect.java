package com.header.correlation.infra.intercepter;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StopWatch;

@Slf4j
@Aspect
public class LoggingAspect {

//    @Before("execution(* *.api.*(..))")
//    public void before() {
//        log.info("[REQ]");
//    }
//
//    @After("execution(* *.api.*(..))")
//    public void after() {
//        log.info("[RES]");
//    }
    @Around("execution(* *.api.*(..))")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        String reqInfo = new StringBuilder(pjp.getTarget().getClass().getName())
                .append(".")
                .append(pjp.getSignature().getName())
                .append(pjp.getArgs())
                .toString();
        log.info("[REQ] {}", reqInfo);
        StopWatch sw = new StopWatch();
        sw.start();

        Object result = pjp.proceed();

        sw.stop();
        Long duration = sw.getTotalTimeMillis();
        String resInfo = new StringBuilder(pjp.getTarget().getClass().getName())
                .append(".")
                .append(pjp.getSignature().getName())
                .append(result)
                .toString();
        log.info("[RES] {} {} ms", resInfo, duration);
        return result;
    }
}
