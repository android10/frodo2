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

  public void printObservableInfo(RxComponentInfo rxComponentInfo) {
    final String message = messageBuilder.buildObservableInfoMessage(rxComponentInfo);
    this.printMessage(rxComponentInfo.classSimpleName(), message);
  }

  public void printObservableOnSubscribe(RxComponentInfo rxComponentInfo) {
    final String message = messageBuilder.buildObservableOnSubscribeMessage(rxComponentInfo);
    this.printMessage(rxComponentInfo.classSimpleName(), message);
  }

  public <T> void printObservableOnNextWithValue(RxComponentInfo rxComponentInfo, T value) {
    final String message =
        messageBuilder.buildObservableOnNextWithValueMessage(rxComponentInfo, value);
    this.printMessage(rxComponentInfo.classSimpleName(), message);
  }

  public void printObservableOnError(RxComponentInfo rxComponentInfo,
      Throwable throwable) {
    final String message =
        messageBuilder.buildObservableOnErrorMessage(rxComponentInfo, throwable.getMessage());
    this.printMessage(rxComponentInfo.classSimpleName(), message);
  }

  public void printObservableOnCompleted(RxComponentInfo rxComponentInfo) {
    final String message = messageBuilder.buildObservableOnCompletedMessage(rxComponentInfo);
    this.printMessage(rxComponentInfo.classSimpleName(), message);
  }

  public void printObservableOnTerminate(RxComponentInfo rxComponentInfo) {
    final String message = messageBuilder.buildObservableOnTerminateMessage(rxComponentInfo);
    this.printMessage(rxComponentInfo.classSimpleName(), message);
  }

  public void printObservableOnUnsubscribe(RxComponentInfo rxComponentInfo) {
    final String message = messageBuilder.buildObservableOnUnsubscribeMessage(rxComponentInfo);
    this.printMessage(rxComponentInfo.classSimpleName(), message);
  }

  public void printObservableItemTimeInfo(RxComponentInfo rxComponentInfo) {
    final String message = messageBuilder.buildObservableItemTimeInfoMessage(rxComponentInfo);
    this.printMessage(rxComponentInfo.classSimpleName(), message);
  }

  public void printObservableThreadInfo(RxComponentInfo rxComponentInfo) {
    if (rxComponentInfo.subscribeOnThread() != null ||
        rxComponentInfo.observeOnThread() != null) {
      final String message = messageBuilder.buildObservableThreadInfoMessage(rxComponentInfo);
      this.printMessage(rxComponentInfo.classSimpleName(), message);
    }
  }
}
