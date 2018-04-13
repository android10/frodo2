package com.fernandocejas.frodo2.logger.flowable;

import com.fernandocejas.frodo2.logger.joinpoint.FrodoProceedingJoinPoint;
import com.fernandocejas.frodo2.logger.joinpoint.RxComponent;
import com.fernandocejas.frodo2.logger.joinpoint.RxComponentInfo;
import com.fernandocejas.frodo2.logger.logging.Counter;
import com.fernandocejas.frodo2.logger.logging.MessageManager;
import com.fernandocejas.frodo2.logger.logging.StopWatch;

import org.reactivestreams.Subscription;

import io.reactivex.Flowable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.LongConsumer;

@SuppressWarnings({"unchecked", "Convert2Lambda"})
class FrodoForFlowable {

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
        .doOnSubscribe(new Consumer<Subscription>() {
          @Override
          public void accept(Subscription subscription) throws Exception {
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
            messageManager.printOnComplete(rxComponentInfo);
          }
        })
        .doOnTerminate(new Action() {
          @Override
          public void run() throws Exception {
            messageManager.printOnTerminate(rxComponentInfo);
          }
        })
        .doOnRequest(new LongConsumer() {
          @Override
          public void accept(long count) throws Exception {
            messageManager.printOnRequestWithCount(rxComponentInfo, count);
          }
        })
        .doOnCancel(new Action() {
          @Override
          public void run() throws Exception {
            messageManager.printOnCancel(rxComponentInfo);
          }
        })
        .doFinally(new Action() {
          @Override
          public void run() throws Exception {
            stopWatch.stop();
            rxComponentInfo.setTotalExecutionTime(stopWatch.getTotalTimeMillis());
            rxComponentInfo.setTotalEmittedItems(emittedItems.tally());
            messageManager.printItemTimeInfo(rxComponentInfo);
            messageManager.printThreadInfo(rxComponentInfo);
          }
        });
  }
}
