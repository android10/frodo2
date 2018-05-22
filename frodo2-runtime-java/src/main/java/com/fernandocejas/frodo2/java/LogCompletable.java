package com.fernandocejas.frodo2.java;

import com.fernandocejas.frodo2.logger.completable.CompletableWeaver;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class LogCompletable {

  @Pointcut(CompletableWeaver.POINTCUT)
  public static boolean methodAnnotatedWithRxLogCompletable(ProceedingJoinPoint joinPoint) {
    return CompletableWeaver.methodAnnotatedWithRxLogCompletable(joinPoint);
  }

  @Around(CompletableWeaver.ADVICE)
  public Object weaveAroundJoinPoint(ProceedingJoinPoint joinPoint) throws Throwable {
    return new CompletableWeaver().weaveAroundJoinPoint(joinPoint, new JavaLog());
  }
}
