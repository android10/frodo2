package com.fernandocejas.frodo2.android;

import com.fernandocejas.frodo2.logger.flowable.FlowableWeaver;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class LogFlowable {

  @Pointcut(FlowableWeaver.POINTCUT)
  public static boolean methodAnnotatedWithRxLogFlowable(ProceedingJoinPoint joinPoint) {
    return FlowableWeaver.methodAnnotatedWithRxLogFlowable(joinPoint);
  }

  @Around(FlowableWeaver.ADVICE)
  public Object weaveAroundJoinPoint(ProceedingJoinPoint joinPoint) throws Throwable {
    return new FlowableWeaver().weaveAroundJoinPoint(joinPoint, new AndroidLog());
  }
}
