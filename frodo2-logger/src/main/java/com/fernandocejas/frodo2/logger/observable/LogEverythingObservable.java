package com.fernandocejas.frodo2.logger.observable;

import com.fernandocejas.frodo2.logger.logging.Counter;
import com.fernandocejas.frodo2.logger.logging.StopWatch;
import com.fernandocejas.frodo2.logger.joinpoint.FrodoProceedingJoinPoint;
import com.fernandocejas.frodo2.logger.logging.MessageManager;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

@SuppressWarnings("unchecked")
class LogEverythingObservable extends LoggableObservable {

  private final ObservableInfo observableInfo;

  LogEverythingObservable(FrodoProceedingJoinPoint joinPoint, MessageManager messageManager,
                          ObservableInfo observableInfo) {
    super(joinPoint, messageManager);
    this.observableInfo = observableInfo;
  }

  @Override <T> Observable<T> get(T type) throws Throwable {
    final StopWatch stopWatch = new StopWatch();
    final Counter emittedItems = new Counter(joinPoint.getMethodName());
    return ((Observable<T>) joinPoint.proceed())
        .doOnSubscribe(new Consumer<Disposable>() {
          @Override public void accept(Disposable disposable) {
            stopWatch.start();
            messageManager.printObservableOnSubscribe(observableInfo);
          }
        })
        //.doOnEach(new Consumer<Notification<T>>() {
        //  @Override public void accept(Notification<T> notification) {
        //    if (!observableInfo.getSubscribeOnThread().isPresent()
        //        && (notification.isOnNext() || notification.isOnError())) {
        //      observableInfo.setSubscribeOnThread(Thread.currentThread().getName());
        //    }
        //  }
        //})
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
        });
        //.doOnDispose(new Action() {
        //  @Override public void run() {
        //    if (!observableInfo.getObserveOnThread().isPresent()) {
        //      observableInfo.setObserveOnThread(Thread.currentThread().getName());
        //    }
        //    messageManager.printObservableThreadInfo(observableInfo);
        //    messageManager.printObservableOnUnsubscribe(observableInfo);
        //  }
        //});
  }
}
