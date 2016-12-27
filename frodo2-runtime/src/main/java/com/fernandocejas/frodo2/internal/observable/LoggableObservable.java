package com.fernandocejas.frodo2.internal.observable;

import com.fernandocejas.frodo2.internal.MessageManager;
import com.fernandocejas.frodo2.joinpoint.FrodoProceedingJoinPoint;
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
