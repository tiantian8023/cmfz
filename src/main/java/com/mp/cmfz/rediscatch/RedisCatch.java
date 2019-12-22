package com.mp.cmfz.rediscatch;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;

@Aspect
@Configuration
public class RedisCatch {

    @Autowired
    private RedisTemplate redisTemplate;

    @Around("@annotation(com.mp.cmfz.myannotation.findCatch)")
    public Object around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        StringBuilder sb = new StringBuilder();
        // 获取目标方法的类全限定名
        String className = proceedingJoinPoint.getTarget().getClass().getName();
        // 获取目标方法的方法名
        String methodName = proceedingJoinPoint.getSignature().getName();
        sb.append(methodName);
        Object[] args = proceedingJoinPoint.getArgs();
        for (Object arg : args) {
            sb.append(arg);
        }
        String s = sb.toString();
        HashOperations hashOperations = redisTemplate.opsForHash();
        Boolean aBoolean = hashOperations.hasKey(className, s);
        Object result = null;
        if (aBoolean) {
            result = hashOperations.get(className, s);
            System.out.println(" redis ");
        } else {
            result = proceedingJoinPoint.proceed();
            hashOperations.put(className, s, result);
            System.out.println(" mysql ");
        }
        return result;
    }

    @After("@annotation(com.mp.cmfz.myannotation.DeleteCatch)")
    public void after(JoinPoint joinPoint) {
        String name = joinPoint.getTarget().getClass().getName();
        redisTemplate.delete(name);
    }
}