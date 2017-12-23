package com.fernandocejas.frodo2.logger.single;

import com.fernandocejas.frodo2.logger.joinpoint.FrodoProceedingJoinPoint;
import com.fernandocejas.frodo2.logger.joinpoint.RxComponent;
import com.fernandocejas.frodo2.logger.joinpoint.RxComponentInfo;
import com.fernandocejas.frodo2.logger.logging.MessageManager;
import com.fernandocejas.frodo2.logger.logging.StopWatch;
import io.reactivex.Single;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.BiConsumer;
import io.reactivex.functions.Consumer;

@SuppressWarnings({ "unchecked", "Convert2Lambda" }) class FrodoForSingle {

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
        .doOnSubscribe(new Consumer<Disposable>() {
          @Override public void accept(Disposable disposable) throws Exception {
            stopWatch.start();
            messageManager.printOnSubscribe(rxComponentInfo);
          }
        })
        .doOnSuccess(new Consumer<T>() {
          @Override public void accept(T value) throws Exception {
            FrodoForSingle.this.messageManager.printOnNextWithValue(FrodoForSingle.this.rxComponentInfo, value);
          }
        })
        .doOnError(new Consumer<Throwable>() {
          @Override public void accept(Throwable throwable) throws Exception {
            FrodoForSingle.this.messageManager.printOnError(FrodoForSingle.this.rxComponentInfo,throwable);
          }
        })
        .doOnEvent(new BiConsumer<T, Throwable>() {
          @Override public void accept(T value, Throwable throwable) throws Exception {
            stopWatch.stop();
            FrodoForSingle.this.rxComponentInfo.setTotalEmittedItems(1);
            FrodoForSingle.this.rxComponentInfo.setTotalExecutionTime(stopWatch.getTotalTimeMillis());
            FrodoForSingle.this.rxComponentInfo.setSubscribeOnThread(Thread.currentThread().getName());
            FrodoForSingle.this.messageManager.printOnCompleted(FrodoForSingle.this.rxComponentInfo);
            FrodoForSingle.this.messageManager.printOnTerminate(FrodoForSingle.this.rxComponentInfo);
            FrodoForSingle.this.messageManager.printItemTimeInfo(FrodoForSingle.this.rxComponentInfo);
          }
        })
        .doFinally(new Action() {
          @Override public void run() throws Exception {
            rxComponentInfo.setObserveOnThread(Thread.currentThread().getName());
            messageManager.printThreadInfo(rxComponentInfo);
            messageManager.printOnUnsubscribe(rxComponentInfo);
          }
        });
  }
}
