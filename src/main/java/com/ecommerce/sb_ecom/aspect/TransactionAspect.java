package com.ecommerce.sb_ecom.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class TransactionAspect {
    @Before("@annotation(org.springframework.transaction.annotation.Transactional)")
    public void beforeTransactionalMethod(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().getSimpleName();
        System.out.println("Starting Transaction: Method " + methodName + " in class " + className);
    }

    @AfterReturning("@annotation(org.springframework.transaction.annotation.Transactional)")
    public void afterSuccessfulTransactionalMethod(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().getSimpleName();
        System.out.println("✅ Transaction committed: Method " + methodName + " in class " + className);
    }

    @AfterThrowing(value = "@annotation(org.springframework.transaction.annotation.Transactional)", throwing = "ex")
    public void afterTransactionException(JoinPoint joinPoint, Throwable ex) {
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().getSimpleName();
        System.out.println("❌ Transaction rolled back: Method " + methodName + " in class " + className);
        System.out.println("Exception: " + ex.getMessage());
    }
}
