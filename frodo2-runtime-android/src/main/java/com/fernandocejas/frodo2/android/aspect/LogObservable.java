package com.fernandocejas.frodo2.android.aspect;

import com.fernandocejas.frodo2.android.internal.MessageManager;
import com.fernandocejas.frodo2.android.internal.observable.FrodoObservable;
import com.fernandocejas.frodo2.android.joinpoint.FrodoProceedingJoinPoint;
import io.reactivex.Observable;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

@Aspect
public class LogObservable {
  private static final String METHOD =
      "execution(@com.fernandocejas.frodo2.annotation.RxLogObservable * *(..)) && if()";

  @Pointcut(METHOD)
  public static boolean methodAnnotatedWithRxLogObservable(ProceedingJoinPoint joinPoint) {
    return ((MethodSignature) joinPoint.getSignature()).getReturnType() == Observable.class;
  }

  @Around("methodAnnotatedWithRxLogObservable(joinPoint)")
  public Object weaveAroundJoinPoint(ProceedingJoinPoint joinPoint) throws Throwable {
    final FrodoProceedingJoinPoint proceedingJoinPoint = new FrodoProceedingJoinPoint(joinPoint);
    final MessageManager messageManager = new MessageManager();
    return new FrodoObservable(proceedingJoinPoint, messageManager).getObservable();
  }
}
