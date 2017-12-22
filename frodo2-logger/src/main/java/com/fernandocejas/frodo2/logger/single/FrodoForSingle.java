package com.fernandocejas.frodo2.logger.single;

import com.fernandocejas.frodo2.logger.joinpoint.FrodoProceedingJoinPoint;
import com.fernandocejas.frodo2.logger.joinpoint.RxComponentInfo;
import com.fernandocejas.frodo2.logger.logging.MessageManager;
import com.fernandocejas.frodo2.logger.logging.StopWatch;
import io.reactivex.Single;

@SuppressWarnings("unchecked") class FrodoForSingle {

  private final FrodoProceedingJoinPoint joinPoint;
  private final MessageManager messageManager;
  private final RxComponentInfo rxComponentInfo;

  FrodoForSingle(FrodoProceedingJoinPoint joinPoint, MessageManager messageManager) {
    this.joinPoint = joinPoint;
    this.messageManager = messageManager;
    this.rxComponentInfo = new RxComponentInfo(joinPoint);
  }

  Single single() throws Throwable {
    messageManager.printObservableInfo(rxComponentInfo);
    final Class singleType = joinPoint.getGenericReturnTypes().get(0);
    return loggableSingle(singleType);
  }

  private <T> Single<T> loggableSingle(T type) throws Throwable {
    final StopWatch stopWatch = new StopWatch();
    return ((Single<T>) joinPoint.proceed())
        .doOnSubscribe(disposable -> {
          stopWatch.start();
          messageManager.printObservableOnSubscribe(rxComponentInfo);
        })
        .doOnSuccess(value -> messageManager.printObservableOnNextWithValue(rxComponentInfo, value))
        .doOnError(throwable -> messageManager.printObservableOnError(rxComponentInfo, throwable))
        .doOnEvent((value, throwable) -> {
          stopWatch.stop();
          rxComponentInfo.setTotalEmittedItems(1);
          rxComponentInfo.setTotalExecutionTime(stopWatch.getTotalTimeMillis());
          rxComponentInfo.setSubscribeOnThread(Thread.currentThread().getName());
          messageManager.printObservableOnCompleted(rxComponentInfo);
          messageManager.printObservableOnTerminate(rxComponentInfo);
          messageManager.printObservableItemTimeInfo(rxComponentInfo);
        })
        .doFinally(() -> {
          rxComponentInfo.setObserveOnThread(Thread.currentThread().getName());
          messageManager.printObservableThreadInfo(rxComponentInfo);
          messageManager.printObservableOnUnsubscribe(rxComponentInfo);
        });
  }
}
