package com.fernandocejas.frodo2.android;

import com.fernandocejas.frodo2.logger.maybe.MaybeWeaver;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class LogMaybe {

  @Pointcut(MaybeWeaver.POINTCUT)
  public static boolean methodAnnotatedWithRxLogMaybe(ProceedingJoinPoint joinPoint) {
    return MaybeWeaver.methodAnnotatedWithRxLogMaybe(joinPoint);
  }

  @Around(MaybeWeaver.ADVICE)
  public Object weaveAroundJoinPoint(ProceedingJoinPoint joinPoint) throws Throwable {
    return new MaybeWeaver().weaveAroundJoinPoint(joinPoint, new AndroidLog());
  }
}
