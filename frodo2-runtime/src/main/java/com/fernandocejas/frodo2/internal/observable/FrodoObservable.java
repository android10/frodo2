package com.fernandocejas.frodo2.internal.observable;

import com.fernandocejas.frodo2.internal.MessageManager;
import com.fernandocejas.frodo2.joinpoint.FrodoProceedingJoinPoint;
import io.reactivex.Observable;

@SuppressWarnings("unchecked")
public class FrodoObservable {

  private final FrodoProceedingJoinPoint joinPoint;
  private final MessageManager messageManager;
  private final ObservableInfo observableInfo;

  public FrodoObservable(FrodoProceedingJoinPoint joinPoint, MessageManager messageManager) {
    this.joinPoint = joinPoint;
    this.messageManager = messageManager;
    this.observableInfo = new ObservableInfo(joinPoint);
  }

  public Observable getObservable() throws Throwable {
    messageManager.printObservableInfo(observableInfo);
    final Class observableType = joinPoint.getGenericReturnTypes().get(0);
    return new LogEverythingObservable(joinPoint, messageManager, observableInfo).get(observableType);
  }
}
