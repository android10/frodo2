package com.fernandocejas.frodo2.logger.observable;

import com.fernandocejas.frodo2.logger.joinpoint.FrodoProceedingJoinPoint;
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
  private final ObservableInfo observableInfo;

  FrodoForObservable(FrodoProceedingJoinPoint joinPoint, MessageManager messageManager) {
    this.joinPoint = joinPoint;
    this.messageManager = messageManager;
    this.observableInfo = new ObservableInfo(joinPoint);
  }

  Observable observable() throws Throwable {
    messageManager.printObservableInfo(observableInfo);
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
            messageManager.printObservableOnSubscribe(observableInfo);
          }
        })
        .doOnEach(new Consumer<Notification<T>>() {
          @Override public void accept(Notification<T> notification) {
            if (observableInfo.subscribeOnThread() != null
                && (notification.isOnNext() || notification.isOnError())) {
              observableInfo.setSubscribeOnThread(Thread.currentThread().getName());
            }
          }
        })
        .doOnNext(new Consumer<T>() {
          @Override public void accept(T value) {
            emittedItems.increment();
            messageManager.printObservableOnNextWithValue(observableInfo, value);
          }
        })
        .doOnError(new Consumer<Throwable>() {
          @Override public void accept(Throwable throwable) {
            messageManager.printObservableOnError(observableInfo, throwable);
          }
        })
        .doOnComplete(new Action() {
          @Override public void run() {
            messageManager.printObservableOnCompleted(observableInfo);
          }
        })
        .doOnTerminate(new Action() {
          @Override public void run() {
            stopWatch.stop();
            observableInfo.setTotalExecutionTime(stopWatch.getTotalTimeMillis());
            observableInfo.setTotalEmittedItems(emittedItems.tally());
            messageManager.printObservableOnTerminate(observableInfo);
            messageManager.printObservableItemTimeInfo(observableInfo);
          }
        })
    .doFinally(new Action() {
      @Override public void run() {
        if (observableInfo.observeOnThread() != null) {
          observableInfo.setObserveOnThread(Thread.currentThread().getName());
        }
        messageManager.printObservableThreadInfo(observableInfo);
        messageManager.printObservableOnUnsubscribe(observableInfo);
      }
    });
  }
}
