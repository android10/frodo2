package com.fernandocejas.frodo2.logger.completable;

import com.fernandocejas.frodo2.logger.joinpoint.FrodoProceedingJoinPoint;
import com.fernandocejas.frodo2.logger.joinpoint.RxComponent;
import com.fernandocejas.frodo2.logger.joinpoint.RxComponentInfo;
import com.fernandocejas.frodo2.logger.logging.MessageManager;
import com.fernandocejas.frodo2.test.TestJoinPoint;
import com.fernandocejas.frodo2.test.TestProceedingJoinPoint;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import io.reactivex.Completable;

class CompletableRule implements TestRule {

  static final String OBSERVABLE_STREAM_VALUE = "fernando";

  private final Class declaringType;

  private FrodoProceedingJoinPoint frodoProceedingJoinPoint;
  private RxComponentInfo rxComponentInfo;

  @Mock private MessageManager messageManager;

  CompletableRule(Class declaringType) {
    MockitoAnnotations.initMocks(this);
    this.declaringType = declaringType;
  }

  @Override public Statement apply(Statement statement, Description description) {
    final TestJoinPoint testJoinPoint =
        new TestJoinPoint.Builder(declaringType)
            .withReturnType(Completable.class)
            .withReturnValue(OBSERVABLE_STREAM_VALUE)
            .build();

    final TestProceedingJoinPoint testProceedingJoinPoint =
        new TestProceedingJoinPoint(testJoinPoint);

    frodoProceedingJoinPoint = new FrodoProceedingJoinPoint(testProceedingJoinPoint);
    rxComponentInfo = new RxComponentInfo(RxComponent.COMPLETABLE, frodoProceedingJoinPoint);

    return statement;
  }

  FrodoProceedingJoinPoint joinPoint() {
    return frodoProceedingJoinPoint;
  }

  MessageManager messageManager() {
    return messageManager;
  }

  RxComponentInfo info() {
    return rxComponentInfo;
  }

  String stringType() {
    return "";
  }
}
