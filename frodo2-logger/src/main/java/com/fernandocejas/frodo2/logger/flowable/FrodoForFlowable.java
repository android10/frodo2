package com.fernandocejas.frodo2.logger.flowable;

import com.fernandocejas.frodo2.logger.joinpoint.FrodoProceedingJoinPoint;
import com.fernandocejas.frodo2.logger.joinpoint.RxComponent;
import com.fernandocejas.frodo2.logger.joinpoint.RxComponentInfo;
import com.fernandocejas.frodo2.logger.logging.Counter;
import com.fernandocejas.frodo2.logger.logging.MessageManager;
import com.fernandocejas.frodo2.logger.logging.StopWatch;
import io.reactivex.Flowable;

@SuppressWarnings("unchecked") class FrodoForFlowable {

  private final FrodoProceedingJoinPoint joinPoint;
  private final MessageManager messageManager;
  private final RxComponentInfo rxComponentInfo;

  FrodoForFlowable(FrodoProceedingJoinPoint joinPoint, MessageManager messageManager) {
    this.joinPoint = joinPoint;
    this.messageManager = messageManager;
    this.rxComponentInfo = new RxComponentInfo(RxComponent.FLOWABLE, joinPoint);
  }

  Flowable flowable() throws Throwable {
    messageManager.printRxComponentInfo(rxComponentInfo);
    final Class flowableType = joinPoint.getGenericReturnTypes().get(0);
    return loggableFlowable(flowableType);
  }

  private <T> Flowable<T> loggableFlowable(T type) throws Throwable {
    final StopWatch stopWatch = new StopWatch();
    final Counter emittedItems = new Counter(joinPoint.getMethodName());
    return ((Flowable<T>) joinPoint.proceed())
        .doOnSubscribe(subscription -> {
          stopWatch.start();
          messageManager.printOnSubscribe(rxComponentInfo);
        })
        .doOnEach(notification -> {
          if (rxComponentInfo.subscribeOnThread() != null && (notification.isOnNext()
              || notification.isOnError())) {
            rxComponentInfo.setSubscribeOnThread(Thread.currentThread().getName());
          }
        })
        .doOnNext(value -> {
          emittedItems.increment();
          messageManager.printOnNextWithValue(rxComponentInfo, value);
        })
        .doOnError(throwable -> messageManager.printOnError(rxComponentInfo, throwable))
        .doOnComplete(() -> messageManager.printOnCompleted(rxComponentInfo))
        .doOnTerminate(() -> {
          stopWatch.stop();
          rxComponentInfo.setTotalExecutionTime(stopWatch.getTotalTimeMillis());
          rxComponentInfo.setTotalEmittedItems(emittedItems.tally());
          messageManager.printOnTerminate(rxComponentInfo);
          messageManager.printItemTimeInfo(rxComponentInfo);
        })
        .doFinally(() -> {
          if (rxComponentInfo.observeOnThread() != null) {
            rxComponentInfo.setObserveOnThread(Thread.currentThread().getName());
          }
          messageManager.printThreadInfo(rxComponentInfo);
          messageManager.printOnUnsubscribe(rxComponentInfo);
        });
  }
}
