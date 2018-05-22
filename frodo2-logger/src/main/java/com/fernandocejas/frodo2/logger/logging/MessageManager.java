package com.fernandocejas.frodo2.logger.logging;

import com.fernandocejas.frodo2.logger.joinpoint.RxComponentInfo;
import com.fernandocejas.frodo2.logging.Logger;

public class MessageManager {

  private final MessageBuilder messageBuilder;
  private final Logger logger;

  public MessageManager(MessageBuilder messageBuilder, Logger logger) {
    this.messageBuilder = messageBuilder;
    this.logger = logger;
  }

  private void printMessage(String tag, String message) {
    logger.log(tag, message);
  }

  public void printRxComponentInfo(RxComponentInfo rxComponentInfo) {
    final String message = messageBuilder.buildRxComponentInfoMessage(rxComponentInfo);
    this.printMessage(rxComponentInfo.classSimpleName(), message);
  }

  public void printOnSubscribe(RxComponentInfo rxComponentInfo) {
    final String message = messageBuilder.buildOnSubscribeMessage(rxComponentInfo);
    this.printMessage(rxComponentInfo.classSimpleName(), message);
  }

  public <T> void printOnNextWithValue(RxComponentInfo rxComponentInfo, T value) {
    final String message = messageBuilder.buildOnNextWithValueMessage(rxComponentInfo, value);
    this.printMessage(rxComponentInfo.classSimpleName(), message);
  }

  public <T> void printOnSuccessWithValue(RxComponentInfo rxComponentInfo, T value) {
    final String message = messageBuilder.buildOnSuccessWithValueMessage(rxComponentInfo, value);
    this.printMessage(rxComponentInfo.classSimpleName(), message);
  }

  public void printOnError(RxComponentInfo rxComponentInfo,
      Throwable throwable) {
    final String message = messageBuilder.buildOnErrorMessage(rxComponentInfo, throwable.getMessage());
    this.printMessage(rxComponentInfo.classSimpleName(), message);
  }

  public void printOnComplete(RxComponentInfo rxComponentInfo) {
    final String message = messageBuilder.buildOnCompleteMessage(rxComponentInfo);
    this.printMessage(rxComponentInfo.classSimpleName(), message);
  }

  public void printOnTerminate(RxComponentInfo rxComponentInfo) {
    final String message = messageBuilder.buildOnTerminateMessage(rxComponentInfo);
    this.printMessage(rxComponentInfo.classSimpleName(), message);
  }

  public void printOnDispose(RxComponentInfo rxComponentInfo) {
    final String message = messageBuilder.buildOnDisposeMessage(rxComponentInfo);
    this.printMessage(rxComponentInfo.classSimpleName(), message);
  }

  public void printOnRequestWithCount(RxComponentInfo rxComponentInfo, long count) {
    final String message = messageBuilder.buildOnRequestWithCountMessage(rxComponentInfo, count);
    this.printMessage(rxComponentInfo.classSimpleName(), message);
  }

  public void printOnCancel(RxComponentInfo rxComponentInfo) {
    final String message = messageBuilder.buildOnCancelMessage(rxComponentInfo);
    this.printMessage(rxComponentInfo.classSimpleName(), message);
  }

  public void printItemTimeInfo(RxComponentInfo rxComponentInfo) {
    final String message = messageBuilder.buildItemTimeInfoMessage(rxComponentInfo);
    this.printMessage(rxComponentInfo.classSimpleName(), message);
  }

  public void printThreadInfo(RxComponentInfo rxComponentInfo) {
    if (rxComponentInfo.subscribeOnThread() != null ||
        rxComponentInfo.observeOnThread() != null) {
      final String message = messageBuilder.buildThreadInfoMessage(rxComponentInfo);
      this.printMessage(rxComponentInfo.classSimpleName(), message);
    }
  }
}
