package com.fernandocejas.frodo2.logger.logging;

import com.fernandocejas.frodo2.logger.observable.ObservableInfo;
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

  public void printObservableInfo(ObservableInfo observableInfo) {
    final String message = messageBuilder.buildObservableInfoMessage(observableInfo);
    this.printMessage(observableInfo.classSimpleName(), message);
  }

  public void printObservableOnSubscribe(ObservableInfo observableInfo) {
    final String message = messageBuilder.buildObservableOnSubscribeMessage(observableInfo);
    this.printMessage(observableInfo.classSimpleName(), message);
  }

  public <T> void printObservableOnNextWithValue(ObservableInfo observableInfo, T value) {
    final String message =
        messageBuilder.buildObservableOnNextWithValueMessage(observableInfo, value);
    this.printMessage(observableInfo.classSimpleName(), message);
  }

  public void printObservableOnError(ObservableInfo observableInfo,
      Throwable throwable) {
    final String message =
        messageBuilder.buildObservableOnErrorMessage(observableInfo, throwable.getMessage());
    this.printMessage(observableInfo.classSimpleName(), message);
  }

  public void printObservableOnCompleted(ObservableInfo observableInfo) {
    final String message = messageBuilder.buildObservableOnCompletedMessage(observableInfo);
    this.printMessage(observableInfo.classSimpleName(), message);
  }

  public void printObservableOnTerminate(ObservableInfo observableInfo) {
    final String message = messageBuilder.buildObservableOnTerminateMessage(observableInfo);
    this.printMessage(observableInfo.classSimpleName(), message);
  }

  public void printObservableOnUnsubscribe(ObservableInfo observableInfo) {
    final String message = messageBuilder.buildObservableOnUnsubscribeMessage(observableInfo);
    this.printMessage(observableInfo.classSimpleName(), message);
  }

  public void printObservableItemTimeInfo(ObservableInfo observableInfo) {
    final String message = messageBuilder.buildObservableItemTimeInfoMessage(observableInfo);
    this.printMessage(observableInfo.classSimpleName(), message);
  }

  public void printObservableThreadInfo(ObservableInfo observableInfo) {
    if (observableInfo.subscribeOnThread() != null ||
        observableInfo.observeOnThread() != null) {
      final String message = messageBuilder.buildObservableThreadInfoMessage(observableInfo);
      this.printMessage(observableInfo.classSimpleName(), message);
    }
  }
}
