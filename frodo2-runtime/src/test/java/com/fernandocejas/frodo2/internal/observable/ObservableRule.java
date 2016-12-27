package com.fernandocejas.frodo2.internal.observable;

import com.fernandocejas.frodo2.internal.MessageManager;
import com.fernandocejas.frodo2.joinpoint.FrodoProceedingJoinPoint;
import com.fernandocejas.frodo2.joinpoint.TestJoinPoint;
import com.fernandocejas.frodo2.joinpoint.TestProceedingJoinPoint;
import io.reactivex.Observable;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class ObservableRule implements TestRule {

  static final String OBSERVABLE_STREAM_VALUE = "fernando";

  private final Class declaringType;

  private FrodoProceedingJoinPoint frodoProceedingJoinPoint;
  private ObservableInfo observableInfo;

  @Mock private MessageManager messageManager;

  ObservableRule(Class declaringType) {
    MockitoAnnotations.initMocks(this);
    this.declaringType = declaringType;
  }

  @Override public Statement apply(Statement statement, Description description) {
    final TestJoinPoint testJoinPoint =
        new TestJoinPoint.Builder(declaringType)
            .withReturnType(Observable.class)
            .withReturnValue(OBSERVABLE_STREAM_VALUE)
            .build();
    final TestProceedingJoinPoint testProceedingJoinPoint =
        new TestProceedingJoinPoint(testJoinPoint);
    frodoProceedingJoinPoint = new FrodoProceedingJoinPoint(testProceedingJoinPoint);
    observableInfo = new ObservableInfo(frodoProceedingJoinPoint);
    return statement;
  }

  FrodoProceedingJoinPoint joinPoint() {
    return frodoProceedingJoinPoint;
  }

  MessageManager messageManager() {
    return messageManager;
  }

  ObservableInfo info() {
    return observableInfo;
  }

  String stringType() {
    return "";
  }
}
