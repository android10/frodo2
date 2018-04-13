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
            FrodoForFlowable.this.messageManager.printOnSubscribe(FrodoForFlowable.this.rxComponentInfo);
          }
        })
        .doOnNext(new Consumer<T>() {
          @Override
          public void accept(T value) throws Exception {
            emittedItems.increment();
            FrodoForFlowable.this.messageManager.printOnNextWithValue(FrodoForFlowable.this.rxComponentInfo, value);
          }
        })
        .doOnError(new Consumer<Throwable>() {
          @Override
          public void accept(Throwable throwable) throws Exception {
            FrodoForFlowable.this.messageManager.printOnError(FrodoForFlowable.this.rxComponentInfo, throwable);
          }
        })
        .doOnComplete(new Action() {
          @Override
          public void run() throws Exception {
            FrodoForFlowable.this.messageManager.printOnCompleted(FrodoForFlowable.this.rxComponentInfo);
          }
        })
        .doOnTerminate(new Action() {
          @Override
          public void run() throws Exception {
            stopWatch.stop();
            FrodoForFlowable.this.rxComponentInfo.setTotalExecutionTime(stopWatch.getTotalTimeMillis());
            FrodoForFlowable.this.rxComponentInfo.setTotalEmittedItems(emittedItems.tally());
            FrodoForFlowable.this.messageManager.printOnTerminate(FrodoForFlowable.this.rxComponentInfo);
            FrodoForFlowable.this.messageManager.printItemTimeInfo(FrodoForFlowable.this.rxComponentInfo);
          }
        })
        .doFinally(new Action() {
          @Override
          public void run() throws Exception {
            if (FrodoForFlowable.this.rxComponentInfo.observeOnThread() == null) {
              FrodoForFlowable.this.rxComponentInfo.setObserveOnThread(Thread.currentThread().getName());
            }
            FrodoForFlowable.this.messageManager.printThreadInfo(FrodoForFlowable.this.rxComponentInfo);
            FrodoForFlowable.this.messageManager.printOnUnsubscribe(FrodoForFlowable.this.rxComponentInfo);
          }
        });
  }
}
