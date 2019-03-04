package com.linlihudong.config.annotation;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * Created by kevin on 2018/12/5.
 */
@Aspect
@Component
public class DesensitizationAspect {

    @Pointcut("@annotation(com.linlihudong.config.annotation.MethodDesensitization)")
    public void proxyAspect() {

    }

    @Around("proxyAspect()")
    public Object doInvoke(ProceedingJoinPoint joinPoint) throws Throwable {
        Object result = joinPoint.proceed();
        Object target = joinPoint.getTarget();
        AnnotationUtil.hardlerDesensitization(result);
        return result;
    }

}
