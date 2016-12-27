package com.fernandocejas.frodo2.android.internal.observable;

import com.fernandocejas.frodo2.android.internal.MessageManager;
import com.fernandocejas.frodo2.android.joinpoint.FrodoProceedingJoinPoint;
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
