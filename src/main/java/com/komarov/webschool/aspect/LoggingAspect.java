package com.komarov.webschool.aspect;

import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.stream.Collectors;


@Aspect
@Component
@Log4j2
public class LoggingAspect {

    //TODO: check if all method in the service logged
    @Pointcut("execution(public * com.komarov.webschool.service.implementaion.*.*(..))")
    private void allServicePublicMethods() {

    }

    @Before("allServicePublicMethods()")
    public void beforeAdvice(JoinPoint joinPoint) {
        Signature signature = joinPoint.getSignature();
        String fullClassName = signature.getDeclaringTypeName();
        String methodName = signature.getName();

        String args = Arrays.stream(joinPoint.getArgs()).map(Object::toString).collect(Collectors.joining(","));

        log.debug(String.format("%s.%s(%s)", fullClassName, methodName, args));
    }
}
