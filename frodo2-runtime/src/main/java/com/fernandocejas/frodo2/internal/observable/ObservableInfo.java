package com.fernandocejas.frodo2.internal.observable;

import com.fernandocejas.frodo2.joinpoint.FrodoJoinPoint;

public class ObservableInfo {
  private final FrodoJoinPoint joinPoint;

  private String subscribeOnThread;
  private String observeOnThread;
  private long totalExecutionTime;
  private int totalEmittedItems;

  public ObservableInfo(FrodoJoinPoint joinPoint) {
    this.joinPoint = joinPoint;
  }

  public String getClassSimpleName() {
    return joinPoint.getClassSimpleName();
  }

  public String getMethodName() {
    return joinPoint.getMethodName();
  }

  public FrodoJoinPoint getJoinPoint() {
    return joinPoint;
  }

  public String getSubscribeOnThread() {
    return subscribeOnThread;
  }

  public String getObserveOnThread() {
    return observeOnThread;
  }

  public long getTotalExecutionTime() {
    return totalExecutionTime;
  }

  public int getTotalEmittedItems() {
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
