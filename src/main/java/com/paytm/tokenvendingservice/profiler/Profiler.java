package com.paytm.tokenvendingservice.profiler;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StopWatch;
import org.springframework.util.StopWatch.TaskInfo;

@Aspect
@Configuration
public class Profiler {
  private static final Logger logger = LoggerFactory.getLogger(Profiler.class);

  @Around("@annotation(LogTime) && execution(* *(..))")
  public Object profile(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
    StopWatch stopWatch = new StopWatch();
    stopWatch.start(proceedingJoinPoint.toShortString());
    boolean isExceptionThrown = false;
    try {
      return proceedingJoinPoint.proceed();
    } catch (RuntimeException e) {
      isExceptionThrown = true;
      throw e;
    } finally {
      stopWatch.stop();
      TaskInfo taskInfo = stopWatch.getLastTaskInfo();
      String profileMessage = taskInfo.getTaskName() + ": " + taskInfo.getTimeMillis() + " ms"
          + (isExceptionThrown ? " (thrown Exception)" : "");
      logger.error(profileMessage);
    }
  }

}
