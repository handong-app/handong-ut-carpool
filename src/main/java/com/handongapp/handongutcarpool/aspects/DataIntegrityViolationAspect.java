package com.handongapp.handongutcarpool.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class DataIntegrityViolationAspect {

    @Around("execution(* com.handongapp.handongutcarpool.service.*.create(..))")
    public Object handleDuplicateEntry(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            return joinPoint.proceed();
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException("데이터 무결성 위반 : " + e.getMessage());
        }
    }
}
