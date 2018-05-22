package com.fernandocejas.frodo2.logger.completable;

import com.fernandocejas.frodo2.logger.joinpoint.FrodoProceedingJoinPoint;
import com.fernandocejas.frodo2.logger.joinpoint.RxComponent;
import com.fernandocejas.frodo2.logger.joinpoint.RxComponentInfo;
import com.fernandocejas.frodo2.logger.logging.MessageManager;
import com.fernandocejas.frodo2.logger.logging.StopWatch;

import io.reactivex.Completable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

@SuppressWarnings({"unchecked", "Convert2Lambda"})
class FrodoForCompletable {

  private final FrodoProceedingJoinPoint joinPoint;
  private final MessageManager messageManager;
  private final RxComponentInfo rxComponentInfo;

  FrodoForCompletable(FrodoProceedingJoinPoint joinPoint, MessageManager messageManager) {
    this.joinPoint = joinPoint;
    this.messageManager = messageManager;
    this.rxComponentInfo = new RxComponentInfo(RxComponent.COMPLETABLE, joinPoint);
  }

  Completable completable() throws Throwable {
    messageManager.printRxComponentInfo(rxComponentInfo);
    return loggableCompletable();
  }

  private Completable loggableCompletable() throws Throwable {
    final StopWatch stopWatch = new StopWatch();
    return ((Completable) joinPoint.proceed())
        .doOnSubscribe(new Consumer<Disposable>() {
          @Override
          public void accept(Disposable disposable) throws Exception {
            stopWatch.start();
            messageManager.printOnSubscribe(rxComponentInfo);
          }
        })
        .doOnComplete(new Action() {
          @Override
          public void run() throws Exception {
            messageManager.printOnComplete(rxComponentInfo);
          }
        })
        .doOnError(new Consumer<Throwable>() {
          @Override
          public void accept(Throwable throwable) throws Exception {
            messageManager.printOnError(rxComponentInfo, throwable);
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
            rxComponentInfo.setTotalEmittedItems(0);
            messageManager.printItemTimeInfo(rxComponentInfo);
            messageManager.printThreadInfo(rxComponentInfo);
          }
        });
  }
}
