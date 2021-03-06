package com.kog.mypage.novel.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class LogAspect {

    @Around("within(com.kog.mypage.novel..*) or within(com.kog.mypage.novel.securituy..*)")
    public Object a(ProceedingJoinPoint joinPoint) throws Throwable{
        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
        System.out.println(joinPoint.getTarget());
        return joinPoint.proceed();
    }
}
