package com.fernandocejas.frodo2.java;

import com.fernandocejas.frodo2.logger.single.SingleWeaver;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class LogSingle {

  @Pointcut(SingleWeaver.POINTCUT)
  public static boolean methodAnnotatedWithRxLogSingle(ProceedingJoinPoint joinPoint) {
    return SingleWeaver.methodAnnotatedWithRxLogSingle(joinPoint);
  }

  @Around(SingleWeaver.ADVICE)
  public Object weaveAroundJoinPoint(ProceedingJoinPoint joinPoint) throws Throwable {
    return new SingleWeaver().weaveAroundJoinPoint(joinPoint, new JavaLog());
  }
}
