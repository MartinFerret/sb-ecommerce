package com.ecommerce.sb_ecom.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    @Before("execution(* com.ecommerce.sb_ecom.service.*.*.*(..))")
    public void beforeMethods(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().getSimpleName();
        System.out.println("Method : " + methodName + " called in class: " + className);
    }

    @AfterThrowing(pointcut = "execution(* com.ecommerce.sb_ecom.service.*.*.*(..))", throwing = "ex")
    public void afterThrowingMethods(JoinPoint joinPoint, Exception ex) {
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().getSimpleName();
        System.out.println("Method : " + methodName + " called in class: " + className);
        System.out.println("❌  Exception thrown: " + ex.getMessage());
    }

    @AfterReturning(pointcut = "execution(* com.ecommerce.sb_ecom.service.*.*.*(..))", returning = "result")
    public void afterReturningMethods(JoinPoint joinPoint, Object result) {
        System.out.println("✅ Success : Returned value: " + result);
    }
}
