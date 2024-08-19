package com.handongapp.handongutcarpool.aspects;

import com.handongapp.handongutcarpool.exception.DuplicateException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class DuplicateCheckAspect {

    @Around("execution(* com.handongapp.handongutcarpool.service.*.create(..))")
    public Object handleDuplicateEntry(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            return joinPoint.proceed();
        } catch (DataIntegrityViolationException e) {
            throw new DuplicateException("중복된 데이터가 존재합니다.");
        }
    }
}
