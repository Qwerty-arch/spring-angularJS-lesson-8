package com.oshovskii.market.beans;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import java.util.*;

@Aspect
@Component
public class TimeExecutionProfiler {

    private static final Logger logger = LoggerFactory.getLogger(TimeExecutionProfiler.class);
        /*
        *
        * Заменить устеревшие методы
        *
         */
    private static Hashtable<String, Long> hashTableForTimeMethod  = new Hashtable<String, Long>();
    private static Hashtable<String, Integer> hashTableCount = new Hashtable<String, Integer>();

        @Around("execution(public * com.oshovskii.market.*..*.*(..))")
        public Object profile(ProceedingJoinPoint proceedingJoinPoint) throws Throwable
        {
            long start = System.nanoTime();
            Object output = proceedingJoinPoint.proceed();
            long elapsedTime = System.nanoTime() - start;
            String methodName = proceedingJoinPoint.getSignature().toString();
            if (hashTableForTimeMethod.get(methodName) == null)
            {
                hashTableForTimeMethod.put(methodName, elapsedTime);
                hashTableCount.put(methodName, 1);
            }
            else
            {
                hashTableCount.put(methodName, hashTableCount.get(methodName) + 1);
                hashTableForTimeMethod.put(methodName, hashTableForTimeMethod.get(methodName) + elapsedTime);
            }
            return output;
        }

        @After("execution(public * com.oshovskii.market.*..*.*(..))")
        public void profileTimeResult() {
            logger.info("Start profiling");
            List<Object> keys = Arrays.asList(hashTableForTimeMethod.keySet().toArray());
            logger.info("Totals Used:");
            for (Object name : keys)
            {
                logger.info(name + " : " + (hashTableForTimeMethod.get(name) / 1000000));
            }
            Collections.sort(keys, new Comparator<Object>()
            {
                @Override
                public int compare(Object arg0, Object arg1)
                {
                    return hashTableForTimeMethod.get(arg1).compareTo(hashTableForTimeMethod.get(arg0));
                }
            });
            if (!hashTableForTimeMethod.isEmpty() && !keys.isEmpty()) {
                logger.info("The longest time method is " + keys.get(0) + " " + hashTableForTimeMethod.get(keys.get(0)) / 1000000);
            }
            if (!hashTableCount.isEmpty()) {
                for (String stringName : hashTableCount.keySet()) {
                    Integer count = hashTableCount.get(stringName);
                    logger.info("--- " + "Method " + stringName + " complete : " + count);
//                    hashTableCount.forEach((s, count) -> logger.info("--- " + "Method " + s + " complete : " + count));
                }
            }
            for (Object name : keys)
            {
                logger.info("--" + name + " : " + (hashTableForTimeMethod.get(name) / 1000000));
            }
            logger.info("JVM memory in use = " + (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
            logger.info("End profiling");
        }
}
