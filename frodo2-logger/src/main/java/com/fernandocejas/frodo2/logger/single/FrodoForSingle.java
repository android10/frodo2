package com.fernandocejas.frodo2.logger.single;

import com.fernandocejas.frodo2.logger.joinpoint.FrodoProceedingJoinPoint;
import com.fernandocejas.frodo2.logger.joinpoint.RxComponent;
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
    this.rxComponentInfo = new RxComponentInfo(RxComponent.SINGLE, joinPoint);
  }

  Single single() throws Throwable {
    messageManager.printRxComponentInfo(rxComponentInfo);
    final Class singleType = joinPoint.getGenericReturnTypes().get(0);
    return loggableSingle(singleType);
  }

  private <T> Single<T> loggableSingle(T type) throws Throwable {
    final StopWatch stopWatch = new StopWatch();
    return ((Single<T>) joinPoint.proceed())
        .doOnSubscribe(disposable -> {
          stopWatch.start();
          messageManager.printOnSubscribe(rxComponentInfo);
        })
        .doOnSuccess(value -> messageManager.printOnNextWithValue(rxComponentInfo, value))
        .doOnError(throwable -> messageManager.printOnError(rxComponentInfo, throwable))
        .doOnEvent((value, throwable) -> {
          stopWatch.stop();
          rxComponentInfo.setTotalEmittedItems(1);
          rxComponentInfo.setTotalExecutionTime(stopWatch.getTotalTimeMillis());
          rxComponentInfo.setSubscribeOnThread(Thread.currentThread().getName());
          messageManager.printOnCompleted(rxComponentInfo);
          messageManager.printOnTerminate(rxComponentInfo);
          messageManager.printItemTimeInfo(rxComponentInfo);
        })
        .doFinally(() -> {
          rxComponentInfo.setObserveOnThread(Thread.currentThread().getName());
          messageManager.printThreadInfo(rxComponentInfo);
          messageManager.printOnUnsubscribe(rxComponentInfo);
        });
  }
}
