package com.fernandocejas.frodo2.java.aspect;

import com.fernandocejas.frodo2.java.logging.JavaLog;
import com.fernandocejas.frodo2.logger.observable.ObservableWeaver;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class LogObservable {

  @Pointcut(ObservableWeaver.POINTCUT)
  public static boolean methodAnnotatedWithRxLogObservable(ProceedingJoinPoint joinPoint) {
    return ObservableWeaver.methodAnnotatedWithRxLogObservable(joinPoint);
  }

  @Around(ObservableWeaver.ADVICE)
  public Object weaveAroundJoinPoint(ProceedingJoinPoint joinPoint) throws Throwable {
    return new ObservableWeaver().weaveAroundJoinPoint(joinPoint, new JavaLog());
  }
}
