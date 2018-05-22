package com.fernandocejas.frodo2.test;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.reflect.MethodSignature;
import org.aspectj.lang.reflect.SourceLocation;
import org.aspectj.runtime.internal.AroundClosure;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.Single;

import static org.assertj.core.api.Assertions.assertThat;

public class TestProceedingJoinPoint implements ProceedingJoinPoint {

  private final TestJoinPoint testJoinPoint;

  //Used for assertions
  private boolean proceedMethodCalled;
  private boolean proceedMethodCalledWithArgs;
  private Object[] proceedMethodArgs;

  public TestProceedingJoinPoint(TestJoinPoint testJoinPoint) {
    this.testJoinPoint = testJoinPoint;

    proceedMethodCalled = false;
    proceedMethodCalledWithArgs = false;
    proceedMethodArgs = new Object[]{};
  }

  private Object buildReturnType() throws InstantiationException, IllegalAccessException {
    final Class returnType = ((MethodSignature) testJoinPoint.getSignature()).getReturnType();
    if (returnType == Observable.class) {
      return Observable.just(testJoinPoint.getMethodReturnValue());
    } else if (returnType == Flowable.class) {
      return Flowable.just(testJoinPoint.getMethodReturnValue());
    } else if (returnType == Single.class) {
      return Single.just(testJoinPoint.getMethodReturnValue());
    } else if (returnType == Maybe.class) {
      return Maybe.just(testJoinPoint.getMethodReturnValue());
    } else if (returnType == Completable.class) {
      return Completable.complete();
    }
    return returnType.newInstance();
  }

  @Override
  public void set$AroundClosure(AroundClosure arc) {
    //do nothing
  }

  @Override
  public Object proceed() throws Throwable {
    proceedMethodCalled = true;
    return buildReturnType();
  }

  @Override
  public Object proceed(Object[] args) throws Throwable {
    proceedMethodCalledWithArgs = true;
    proceedMethodArgs = args;
    return buildReturnType();
  }

  @Override
  public String toShortString() {
    return testJoinPoint.toShortString();
  }

  @Override
  public String toLongString() {
    return testJoinPoint.toLongString();
  }

  @Override
  public Object getThis() {
    return this;
  }

  @Override
  public Object getTarget() {
    return testJoinPoint.getTarget();
  }

  @Override
  public Object[] getArgs() {
    return testJoinPoint.getArgs();
  }

  @Override
  public Signature getSignature() {
    return testJoinPoint.getSignature();
  }

  @Override
  public SourceLocation getSourceLocation() {
    return testJoinPoint.getSourceLocation();
  }

  @Override
  public String getKind() {
    return testJoinPoint.getKind();
  }

  @Override
  public StaticPart getStaticPart() {
    return testJoinPoint.getStaticPart();
  }

  public Class getMethodReturnType() {
    return testJoinPoint.getMethodReturnType();
  }

  public String getMethodReturnValue() {
    return testJoinPoint.getMethodReturnValue();
  }

  public void assertProceedMethodCalled() {
    assertThat(proceedMethodCalled).isTrue();
    proceedMethodCalled = false;
  }

  public void assertProceedMethodCalledWithArgs(Object[] args) {
    assertThat(proceedMethodCalledWithArgs).isTrue();
    assertThat(proceedMethodArgs).isEqualTo(args);
    proceedMethodCalledWithArgs = false;
    proceedMethodArgs = new Object[]{};
  }
}
