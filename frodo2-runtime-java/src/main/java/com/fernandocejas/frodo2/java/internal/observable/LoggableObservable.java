package com.fernandocejas.frodo2.java.internal.observable;

import com.fernandocejas.frodo2.java.internal.MessageManager;
import com.fernandocejas.frodo2.java.joinpoint.FrodoProceedingJoinPoint;
import io.reactivex.Observable;

abstract class LoggableObservable {

  final FrodoProceedingJoinPoint joinPoint;
  final MessageManager messageManager;

  LoggableObservable(FrodoProceedingJoinPoint joinPoint, MessageManager messageManager) {
    this.joinPoint = joinPoint;
    this.messageManager = messageManager;
  }

  abstract <T> Observable<T> get(T type) throws Throwable;
}
