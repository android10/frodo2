package com.fernandocejas.frodo2.logger.observable;

import com.fernandocejas.frodo2.logger.joinpoint.FrodoJoinPoint;

public class ObservableInfo {
  private final FrodoJoinPoint joinPoint;

  private String subscribeOnThread;
  private String observeOnThread;
  private long totalExecutionTime;
  private int totalEmittedItems;

  ObservableInfo(FrodoJoinPoint joinPoint) {
    this.joinPoint = joinPoint;
  }

  public String classSimpleName() {
    return joinPoint.getClassSimpleName();
  }

  public String methodName() {
    return joinPoint.getMethodName();
  }

  public FrodoJoinPoint joinPoint() {
    return joinPoint;
  }

  public String subscribeOnThread() {
    return subscribeOnThread;
  }

  public String observeOnThread() {
    return observeOnThread;
  }

  public long totalExecutionTime() {
    return totalExecutionTime;
  }

  public int totalEmittedItems() {
    return totalEmittedItems;
  }

  void setSubscribeOnThread(String subscribeOnThread) {
    this.subscribeOnThread = subscribeOnThread;
  }

  void setObserveOnThread(String observeOnThread) {
    this.observeOnThread = observeOnThread;
  }

  void setTotalExecutionTime(long totalExecutionTime) {
    this.totalExecutionTime = totalExecutionTime;
  }

  void setTotalEmittedItems(int totalEmittedItems) {
    this.totalEmittedItems = totalEmittedItems;
  }
}
