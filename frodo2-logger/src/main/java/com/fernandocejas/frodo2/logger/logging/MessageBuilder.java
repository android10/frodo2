package com.fernandocejas.frodo2.logger.logging;

import com.fernandocejas.frodo2.logger.joinpoint.RxComponentInfo;
import com.fernandocejas.frodo2.logger.joinpoint.FrodoJoinPoint;

import java.util.List;

/**
 * Class used to build different messages that will be shown in debug mode
 */
public class MessageBuilder {

  private static final String LOG_START = "@";
  private static final String SEPARATOR = " :: ";
  private static final String METHOD_SEPARATOR = "#";
  private static final String VALUE_SEPARATOR = " -> ";
  private static final String TEXT_ENCLOSING_SYMBOL = "'";
  private static final String LOG_ENCLOSING_OPEN = "[";
  private static final String LOG_ENCLOSING_CLOSE = "]";
  private static final String LIBRARY_LABEL = "Frodo2 => ";
  private static final String CLASS_LABEL = LOG_START + "InClass" + VALUE_SEPARATOR;
  private static final String METHOD_LABEL = LOG_START + "Method" + VALUE_SEPARATOR;
  private static final String TIME_LABEL = LOG_START + "Time" + VALUE_SEPARATOR;
  private static final String TIME_MILLIS = " ms";
  private static final String OBSERVABLE_LABEL = LOG_START + "Observable";
  private static final String EMITTED_ELEMENTS_LABEL = LOG_START + "Emitted" + VALUE_SEPARATOR;
  private static final String LABEL_OBSERVABLE_ON_SUBSCRIBE = "onSubscribe()";
  private static final String LABEL_OBSERVABLE_ON_NEXT = "onNext()";
  private static final String LABEL_OBSERVABLE_ON_ERROR = "onError()";
  private static final String LABEL_OBSERVABLE_ON_COMPLETED = "onCompleted()";
  private static final String LABEL_OBSERVABLE_ON_TERMINATE = "onTerminate()";
  private static final String LABEL_OBSERVABLE_ON_UNSUBSCRIBE = "onUnsubscribe()";
  private static final String LABEL_OBSERVABLE_SUBSCRIBE_ON = LOG_START + "SubscribeOn" + VALUE_SEPARATOR;
  private static final String LABEL_SUBSCRIBER_OBSERVE_ON = LOG_START + "ObserveOn" + VALUE_SEPARATOR;
  private static final String LABEL_ELEMENT_SINGULAR = " element";
  private static final String LABEL_ELEMENT_PLURAL = " elements";

  public MessageBuilder() {}

  String buildObservableInfoMessage(RxComponentInfo rxComponentInfo) {
    final FrodoJoinPoint joinPoint = rxComponentInfo.joinPoint();
    final StringBuilder message = buildObservableSB();
    message.append(SEPARATOR);
    message.append(CLASS_LABEL);
    message.append(rxComponentInfo.classSimpleName());
    message.append(SEPARATOR);
    message.append(METHOD_LABEL);
    message.append(rxComponentInfo.methodName());
    message.append(buildMethodSignatureWithValues(joinPoint));
    message.append(LOG_ENCLOSING_CLOSE);

    return message.toString();
  }

  String buildObservableOnSubscribeMessage(RxComponentInfo rxComponentInfo) {
    final StringBuilder message = buildObservableSB();
    message.append(METHOD_SEPARATOR);
    message.append(rxComponentInfo.methodName());
    message.append(VALUE_SEPARATOR);
    message.append(LABEL_OBSERVABLE_ON_SUBSCRIBE);
    message.append(LOG_ENCLOSING_CLOSE);

    return message.toString();
  }

  <T> String buildObservableOnNextWithValueMessage(RxComponentInfo rxComponentInfo, T value) {
    final StringBuilder message = buildObservableSB();
    message.append(METHOD_SEPARATOR);
    message.append(rxComponentInfo.methodName());
    message.append(VALUE_SEPARATOR);
    message.append(LABEL_OBSERVABLE_ON_NEXT);
    message.append(VALUE_SEPARATOR);
    message.append(String.valueOf(value));
    message.append(LOG_ENCLOSING_CLOSE);

    return message.toString();
  }

  String buildObservableOnErrorMessage(RxComponentInfo rxComponentInfo, String errorMessage) {
    final StringBuilder message = buildObservableSB();
    message.append(METHOD_SEPARATOR);
    message.append(rxComponentInfo.methodName());
    message.append(VALUE_SEPARATOR);
    message.append(LABEL_OBSERVABLE_ON_ERROR);
    message.append(VALUE_SEPARATOR);
    message.append(TEXT_ENCLOSING_SYMBOL);
    message.append(errorMessage);
    message.append(TEXT_ENCLOSING_SYMBOL);
    message.append(LOG_ENCLOSING_CLOSE);

    return message.toString();
  }

  String buildObservableOnCompletedMessage(RxComponentInfo rxComponentInfo) {
    final StringBuilder message = buildObservableSB();
    message.append(METHOD_SEPARATOR);
    message.append(rxComponentInfo.methodName());
    message.append(VALUE_SEPARATOR);
    message.append(LABEL_OBSERVABLE_ON_COMPLETED);
    message.append(LOG_ENCLOSING_CLOSE);

    return message.toString();
  }

  String buildObservableOnTerminateMessage(RxComponentInfo rxComponentInfo) {
    final StringBuilder message = buildObservableSB();
    message.append(METHOD_SEPARATOR);
    message.append(rxComponentInfo.methodName());
    message.append(VALUE_SEPARATOR);
    message.append(LABEL_OBSERVABLE_ON_TERMINATE);
    message.append(LOG_ENCLOSING_CLOSE);

    return message.toString();
  }

  String buildObservableOnUnsubscribeMessage(RxComponentInfo rxComponentInfo) {
    final StringBuilder message = buildObservableSB();
    message.append(METHOD_SEPARATOR);
    message.append(rxComponentInfo.methodName());
    message.append(VALUE_SEPARATOR);
    message.append(LABEL_OBSERVABLE_ON_UNSUBSCRIBE);
    message.append(LOG_ENCLOSING_CLOSE);

    return message.toString();
  }

  String buildObservableItemTimeInfoMessage(RxComponentInfo rxComponentInfo) {
    final int totalEmittedItems = rxComponentInfo.totalEmittedItems();
    final long totalExecutionTime = rxComponentInfo.totalExecutionTime();
    final StringBuilder message = buildObservableSB();
    message.append(METHOD_SEPARATOR);
    message.append(rxComponentInfo.methodName());
    message.append(VALUE_SEPARATOR);
    message.append(EMITTED_ELEMENTS_LABEL);
    message.append(totalEmittedItems);
    message.append(totalEmittedItems == 1 ? LABEL_ELEMENT_SINGULAR : LABEL_ELEMENT_PLURAL);
    message.append(SEPARATOR);
    message.append(TIME_LABEL);
    message.append(totalExecutionTime);
    message.append(TIME_MILLIS);
    message.append(LOG_ENCLOSING_CLOSE);

    return message.toString();
  }

  String buildObservableThreadInfoMessage(RxComponentInfo rxComponentInfo) {
    final String subscribeOnThread = rxComponentInfo.subscribeOnThread();
    final String observeOnThread = rxComponentInfo.observeOnThread();
    final StringBuilder message = buildObservableSB();
    message.append(METHOD_SEPARATOR);
    message.append(rxComponentInfo.methodName());
    message.append(VALUE_SEPARATOR);
    if (subscribeOnThread != null) {
      message.append(LABEL_OBSERVABLE_SUBSCRIBE_ON);
      message.append(subscribeOnThread);
    }
    if (observeOnThread != null) {
      message.append(SEPARATOR);
      message.append(LABEL_SUBSCRIBER_OBSERVE_ON);
      message.append(observeOnThread);
    }
    message.append(LOG_ENCLOSING_CLOSE);

    return message.toString();
  }

  private StringBuilder buildObservableSB() {
    final int avgStringSize = 75;
    final StringBuilder message = new StringBuilder(avgStringSize + LIBRARY_LABEL.length());
    message.append(LIBRARY_LABEL);
    message.append(LOG_ENCLOSING_OPEN);
    message.append(OBSERVABLE_LABEL);
    return message;
  }

  private String buildMethodSignatureWithValues(FrodoJoinPoint joinPoint) {
    final int avg = 30;
    final StringBuilder stringBuilder = new StringBuilder(avg + joinPoint.getMethodName().length());
    stringBuilder.append("(");
    List<String> methodParamNames = joinPoint.getMethodParamNamesList();
    if (methodParamNames != null && !methodParamNames.isEmpty()) {
      for (int i = 0; i < joinPoint.getMethodParamNamesList().size(); i++) {
        stringBuilder.append(methodParamNames.get(i));
        stringBuilder.append("=");
        stringBuilder.append("'");
        stringBuilder.append(String.valueOf(joinPoint.getMethodParamValuesList().get(i)));
        stringBuilder.append("'");
        if ((i != methodParamNames.size() - 1)) {
          stringBuilder.append(", ");
        }
      }
    }
    stringBuilder.append(")");

    return stringBuilder.toString();
  }
}
