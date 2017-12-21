package com.fernandocejas.frodo2.logger.observable;

import com.fernandocejas.frodo2.logger.joinpoint.FrodoProceedingJoinPoint;
import com.fernandocejas.frodo2.logger.joinpoint.RxComponentInfo;
import com.fernandocejas.frodo2.logger.logging.Counter;
import com.fernandocejas.frodo2.logger.logging.MessageManager;
import com.fernandocejas.frodo2.logger.logging.StopWatch;
import io.reactivex.Notification;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

@SuppressWarnings("unchecked")
class FrodoForObservable {

  private final FrodoProceedingJoinPoint joinPoint;
  private final MessageManager messageManager;
  private final RxComponentInfo rxComponentInfo;

  FrodoForObservable(FrodoProceedingJoinPoint joinPoint, MessageManager messageManager) {
    this.joinPoint = joinPoint;
    this.messageManager = messageManager;
    this.rxComponentInfo = new RxComponentInfo(joinPoint);
  }

  Observable observable() throws Throwable {
    messageManager.printObservableInfo(rxComponentInfo);
    final Class observableType = joinPoint.getGenericReturnTypes().get(0);
    return loggableObservable(observableType);
  }

  private <T> Observable<T> loggableObservable(T type) throws Throwable {
    final StopWatch stopWatch = new StopWatch();
    final Counter emittedItems = new Counter(joinPoint.getMethodName());
    return ((Observable<T>) joinPoint.proceed())
        .doOnSubscribe(new Consumer<Disposable>() {
          @Override public void accept(Disposable disposable) {
            stopWatch.start();
            messageManager.printObservableOnSubscribe(rxComponentInfo);
          }
        })
        .doOnEach(new Consumer<Notification<T>>() {
          @Override public void accept(Notification<T> notification) {
            if (rxComponentInfo.subscribeOnThread() != null
                && (notification.isOnNext() || notification.isOnError())) {
              rxComponentInfo.setSubscribeOnThread(Thread.currentThread().getName());
            }
          }
        })
        .doOnNext(new Consumer<T>() {
          @Override public void accept(T value) {
            emittedItems.increment();
            messageManager.printObservableOnNextWithValue(rxComponentInfo, value);
          }
        })
        .doOnError(new Consumer<Throwable>() {
          @Override public void accept(Throwable throwable) {
            messageManager.printObservableOnError(rxComponentInfo, throwable);
          }
        })
        .doOnComplete(new Action() {
          @Override public void run() {
            messageManager.printObservableOnCompleted(rxComponentInfo);
          }
        })
        .doOnTerminate(new Action() {
          @Override public void run() {
            stopWatch.stop();
            rxComponentInfo.setTotalExecutionTime(stopWatch.getTotalTimeMillis());
            rxComponentInfo.setTotalEmittedItems(emittedItems.tally());
            messageManager.printObservableOnTerminate(rxComponentInfo);
            messageManager.printObservableItemTimeInfo(rxComponentInfo);
          }
        })
    .doFinally(new Action() {
      @Override public void run() {
        if (rxComponentInfo.observeOnThread() != null) {
          rxComponentInfo.setObserveOnThread(Thread.currentThread().getName());
        }
        messageManager.printObservableThreadInfo(rxComponentInfo);
        messageManager.printObservableOnUnsubscribe(rxComponentInfo);
      }
    });
  }
}
