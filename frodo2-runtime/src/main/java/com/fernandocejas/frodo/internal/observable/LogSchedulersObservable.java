package com.fernandocejas.frodo.internal.observable;

import com.fernandocejas.frodo.internal.MessageManager;
import com.fernandocejas.frodo.joinpoint.FrodoProceedingJoinPoint;
import io.reactivex.Notification;
import io.reactivex.Observable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

@SuppressWarnings("unchecked") class LogSchedulersObservable extends LoggableObservable {
  LogSchedulersObservable(FrodoProceedingJoinPoint joinPoint,
      MessageManager messageManager, ObservableInfo observableInfo) {
    super(joinPoint, messageManager, observableInfo);
  }

  @Override <T> Observable<T> get(T type) throws Throwable {
    return ((Observable<T>) joinPoint.proceed())
        .doOnEach(new Consumer<Notification<T>>() {
          @Override public void accept(Notification<T> notification) {
            if (!observableInfo.getSubscribeOnThread().isPresent()
                && (notification.isOnNext() || notification.isOnError())) {
              observableInfo.setSubscribeOnThread(Thread.currentThread().getName());
            }
          }
        })
        .doOnDispose(new Action() {
          @Override public void run() {
            if (!observableInfo.getObserveOnThread().isPresent()) {
              observableInfo.setObserveOnThread(Thread.currentThread().getName());
            }
            messageManager.printObservableThreadInfo(observableInfo);
          }
        });
  }
}
