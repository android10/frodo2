package com.fernandocejas.frodo2.logger.observable;

import com.fernandocejas.frodo2.logger.joinpoint.FrodoProceedingJoinPoint;
import com.fernandocejas.frodo2.logger.joinpoint.RxComponent;
import com.fernandocejas.frodo2.logger.joinpoint.RxComponentInfo;
import com.fernandocejas.frodo2.logger.logging.Counter;
import com.fernandocejas.frodo2.logger.logging.MessageManager;
import com.fernandocejas.frodo2.logger.logging.StopWatch;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

@SuppressWarnings({"unchecked", "Convert2Lambda"})
class FrodoForObservable {

  private final FrodoProceedingJoinPoint joinPoint;
  private final MessageManager messageManager;
  private final RxComponentInfo rxComponentInfo;

  FrodoForObservable(FrodoProceedingJoinPoint joinPoint, MessageManager messageManager) {
    this.joinPoint = joinPoint;
    this.messageManager = messageManager;
    this.rxComponentInfo = new RxComponentInfo(RxComponent.OBSERVABLE, joinPoint);
  }

  Observable observable() throws Throwable {
    messageManager.printRxComponentInfo(rxComponentInfo);
    final Class observableType = joinPoint.getGenericReturnTypes().get(0);
    return loggableObservable(observableType);
  }

  private <T> Observable<T> loggableObservable(T type) throws Throwable {
    final StopWatch stopWatch = new StopWatch();
    final Counter emittedItems = new Counter(joinPoint.getMethodName());
    return ((Observable<T>) joinPoint.proceed())
            .doOnSubscribe(new Consumer<Disposable>() {
              @Override
              public void accept(Disposable disposable) throws Exception {
                stopWatch.start();
                messageManager.printOnSubscribe(rxComponentInfo);
              }
            })
            .doOnNext(new Consumer<T>() {
              @Override
              public void accept(T value) throws Exception {
                emittedItems.increment();
                messageManager.printOnNextWithValue(rxComponentInfo, value);
              }
            })
            .doOnError(new Consumer<Throwable>() {
              @Override
              public void accept(Throwable throwable) throws Exception {
                messageManager.printOnError(rxComponentInfo, throwable);
              }
            })
            .doOnComplete(new Action() {
              @Override
              public void run() throws Exception {
                messageManager.printOnCompleted(rxComponentInfo);
              }
            })
            .doOnTerminate(new Action() {
              @Override
              public void run() throws Exception {
                stopWatch.stop();
                rxComponentInfo.setTotalExecutionTime(stopWatch.getTotalTimeMillis());
                rxComponentInfo.setTotalEmittedItems(emittedItems.tally());
                messageManager.printOnTerminate(rxComponentInfo);
                messageManager.printItemTimeInfo(rxComponentInfo);
              }
            })
            .doFinally(new Action() {
              @Override
              public void run() throws Exception {
                if (rxComponentInfo.observeOnThread() == null) {
                  rxComponentInfo.setObserveOnThread(Thread.currentThread().getName());
                }
                messageManager.printThreadInfo(rxComponentInfo);
                messageManager.printOnUnsubscribe(rxComponentInfo);
              }
            });
  }
}
