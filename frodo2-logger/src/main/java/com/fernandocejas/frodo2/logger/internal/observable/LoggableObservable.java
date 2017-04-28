package com.fernandocejas.frodo2.logger.internal.observable;

import com.fernandocejas.frodo2.logger.joinpoint.FrodoProceedingJoinPoint;
import com.fernandocejas.frodo2.logger.logging.MessageManager;
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
