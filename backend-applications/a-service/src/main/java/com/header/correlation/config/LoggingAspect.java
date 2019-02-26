package com.header.correlation.config;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StopWatch;
import org.springframework.web.util.UrlPathHelper;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Configuration
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
    @Around("execution(public * com.header.correlation.api.*.*(..))")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        UrlPathHelper uph = new UrlPathHelper();
        String url = uph.getOriginatingRequestUri()

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
