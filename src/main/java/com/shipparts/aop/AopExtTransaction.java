package com.shipparts.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionStatus;

import java.lang.reflect.Method;

@Component
@Aspect
public class AopExtTransaction {

    @Autowired
    private TransactionUtils transactionUtils;

   //异常通知
    @AfterThrowing("execution(com.shipparts.service.UserService.addOrder(..)))")
    public void afterThrowing() {
        System.out.println("程序已经回滚");
        // 获取程序当前事务 进行回滚
        transactionUtils.rollback();
    }

    //环绕通知
    @Around("execution(com.shipparts.service.*.*.*(..)))")
    public void around(ProceedingJoinPoint pjp) throws Throwable {
        //获取方法，判断是否有自定义事务注解
        ExtTransaction extTransaction = getExtTransaction(pjp);
        //开启事务
        TransactionStatus transactionStatus = begin(extTransaction);
        //执行目标代理方法
        pjp.proceed();
        //提交事务
        commit(transactionStatus);
    }

    public ExtTransaction getExtTransaction(ProceedingJoinPoint pjp) throws NoSuchMethodException {
        // 获取方法名称
        String methodName = pjp.getSignature().getName();
        // 获取目标对象
        Class<?> classTarget = pjp.getTarget().getClass();
        // 获取目标对象类型
        Class<?>[] par = ((MethodSignature) pjp.getSignature()).getParameterTypes();
        // 获取目标对象方法
        Method objMethod = classTarget.getMethod(methodName, par);
        ExtTransaction declaredAnnotation = objMethod.getDeclaredAnnotation(ExtTransaction.class);
        if (declaredAnnotation == null) {
            System.out.println("您的方法上,没有加入注解!");
            return null;
        }
        return declaredAnnotation;
    }

    private TransactionStatus begin(ExtTransaction extTransaction) {
        if (extTransaction == null) {
            return null;
        }
        TransactionStatus begin = transactionUtils.begin();
        return begin;
    }

    private void commit(TransactionStatus transactionStatus) {
        if (transactionStatus != null) {
            transactionUtils.commit(transactionStatus);
        }
    }
}
