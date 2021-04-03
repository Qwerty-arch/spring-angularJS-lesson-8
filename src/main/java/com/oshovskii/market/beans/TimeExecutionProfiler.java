package com.oshovskii.market.beans;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import java.util.*;

@Aspect
@Component
@Slf4j
public class TimeExecutionProfiler {

    private final HashMap <String, Long> mapForTimeMethod  = new HashMap<>();
    private final HashMap <String, Integer> mapCount = new HashMap<>();

        @Around("execution(public * com.oshovskii.market.*..*.*(..))")
        public Object profile(ProceedingJoinPoint proceedingJoinPoint) throws Throwable
        {
            long start = System.nanoTime();
            Object output = proceedingJoinPoint.proceed();
            long elapsedTime = System.nanoTime() - start;
            String methodName = proceedingJoinPoint.getSignature().toString();
            if (mapForTimeMethod.get(methodName) == null)
            {
                mapForTimeMethod.put(methodName, elapsedTime);
                mapCount.put(methodName, 1);
            } else
            {
                mapCount.put(methodName, mapCount.get(methodName) + 1);
                mapForTimeMethod.put(methodName, mapForTimeMethod.get(methodName) + elapsedTime);
            }
            return output;
        }

        @After("execution(public * com.oshovskii.market.*..*.*(..))")
        public void profileTimeResult() {
            log.info("Start profiling");
            List<Object> keys = Arrays.asList(mapForTimeMethod.keySet().toArray());
            log.info("Totals Used:");
            for (Object name : keys)
            {
                log.info(name + " : " + (mapForTimeMethod.get(name) / 1000000));
            }
            Collections.sort(keys, new Comparator<Object>()
            {
                @Override
                public int compare(Object arg0, Object arg1)
                {
                    return mapForTimeMethod.get(arg1).compareTo(mapForTimeMethod.get(arg0));
                }
            });
            if (!mapForTimeMethod.isEmpty() && !keys.isEmpty()) {
                log.info("The longest time method is " + keys.get(0) + " " + mapForTimeMethod.get(keys.get(0)) / 1000000);
            }
            if (!mapCount.isEmpty()) {
                for (String stringName : mapCount.keySet()) {
                    Integer count = mapCount.get(stringName);
                    log.info("--- " + "Method " + stringName + " complete : " + count);
                }
            }
            for (Object name : keys)
            {
                log.info("-- " + name + " : " + (mapForTimeMethod.get(name) / 1000000));
            }
            log.info("JVM memory in use = " + (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
            log.info("End profiling");
        }
}
