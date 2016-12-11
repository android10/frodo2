package com.fernandocejas.frodo.internal.observable;

import com.fernandocejas.frodo.internal.Counter;
import com.fernandocejas.frodo.internal.MessageManager;
import com.fernandocejas.frodo.internal.StopWatch;
import com.fernandocejas.frodo.joinpoint.FrodoProceedingJoinPoint;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

@SuppressWarnings("unchecked") class LogStreamObservable extends LoggableObservable {
  LogStreamObservable(FrodoProceedingJoinPoint joinPoint,
      MessageManager messageManager, ObservableInfo observableInfo) {
    super(joinPoint, messageManager, observableInfo);
  }

  @Override <T> Observable<T> get(T type) throws Throwable {
    final StopWatch stopWatch = new StopWatch();
    final Counter emittedItems = new Counter(joinPoint.getMethodName());
    return ((Observable<T>) joinPoint.proceed())
        .doOnSubscribe(new Consumer<Disposable>() {
          @Override public void accept(Disposable disposable) {
            stopWatch.start();
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
        .doOnTerminate(new Action() {
          @Override public void run() {
            stopWatch.stop();
            observableInfo.setTotalExecutionTime(stopWatch.getTotalTimeMillis());
            observableInfo.setTotalEmittedItems(emittedItems.tally());
            messageManager.printObservableItemTimeInfo(observableInfo);
          }
        });
  }
}
