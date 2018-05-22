package com.fernandocejas.frodo2.logger.maybe;

import com.fernandocejas.frodo2.logger.joinpoint.FrodoProceedingJoinPoint;
import com.fernandocejas.frodo2.logger.joinpoint.RxComponent;
import com.fernandocejas.frodo2.logger.joinpoint.RxComponentInfo;
import com.fernandocejas.frodo2.logger.logging.MessageManager;
import com.fernandocejas.frodo2.logger.logging.StopWatch;

import io.reactivex.Maybe;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.BiConsumer;
import io.reactivex.functions.Consumer;

@SuppressWarnings({"unchecked", "Convert2Lambda"})
class FrodoForMaybe {

  private final FrodoProceedingJoinPoint joinPoint;
  private final MessageManager messageManager;
  private final RxComponentInfo rxComponentInfo;

  FrodoForMaybe(FrodoProceedingJoinPoint joinPoint, MessageManager messageManager) {
    this.joinPoint = joinPoint;
    this.messageManager = messageManager;
    this.rxComponentInfo = new RxComponentInfo(RxComponent.MAYBE, joinPoint);
  }

  Maybe maybe() throws Throwable {
    messageManager.printRxComponentInfo(rxComponentInfo);
    final Class singleType = joinPoint.getGenericReturnTypes().get(0);
    return loggableMaybe(singleType);
  }

  private <T> Maybe<T> loggableMaybe(T type) throws Throwable {
    final StopWatch stopWatch = new StopWatch();
    return ((Maybe<T>) joinPoint.proceed())
        .doOnSubscribe(new Consumer<Disposable>() {
          @Override
          public void accept(Disposable disposable) throws Exception {
            stopWatch.start();
            messageManager.printOnSubscribe(rxComponentInfo);
          }
        })
        .doOnSuccess(new Consumer<T>() {
          @Override
          public void accept(T value) throws Exception {

          }
        })
        .doOnError(new Consumer<Throwable>() {
          @Override
          public void accept(Throwable throwable) throws Exception {

          }
        })
        .doOnComplete(new Action() {
          @Override
          public void run() throws Exception {

          }
        })
        .doOnEvent(new BiConsumer<T, Throwable>() {
          @Override
          public void accept(T value, Throwable throwable) throws Exception {
            if (rxComponentInfo.observeOnThread() == null) {
              rxComponentInfo.setObserveOnThread(Thread.currentThread().getName());
            }

            if (value != null) { // Success
              messageManager.printOnSuccessWithValue(rxComponentInfo, value);
              rxComponentInfo.setTotalEmittedItems(1);
            } else if (throwable != null) { // Error
              messageManager.printOnError(rxComponentInfo, throwable);
            } else { // Complete without item
              messageManager.printOnComplete(rxComponentInfo);
            }
          }
        })
        .doOnDispose(new Action() {
          @Override
          public void run() throws Exception {
            messageManager.printOnDispose(rxComponentInfo);
          }
        })
        .doFinally(new Action() {
          @Override
          public void run() throws Exception {
            stopWatch.stop();
            rxComponentInfo.setTotalExecutionTime(stopWatch.getTotalTimeMillis());
            messageManager.printItemTimeInfo(rxComponentInfo);
            messageManager.printThreadInfo(rxComponentInfo);
          }
        });
  }
}
