package com.fernandocejas.frodo2.logger.joinpoint;

import com.fernandocejas.frodo2.test.TestJoinPoint;
import com.fernandocejas.frodo2.test.TestProceedingJoinPoint;
import java.util.Observable;
import org.junit.Before;
import org.junit.Test;

public class FrodoProceedingJoinPointTest {

  private FrodoProceedingJoinPoint frodoJoinPoint;

  private TestJoinPoint joinPoint;
  private TestProceedingJoinPoint proceedingJoinPoint;

  @Before
  public void setUp() throws Exception {
    joinPoint = new TestJoinPoint.Builder(this.getClass()).withReturnType(Observable.class).build();
    proceedingJoinPoint = new TestProceedingJoinPoint(joinPoint);
    frodoJoinPoint = new FrodoProceedingJoinPoint(proceedingJoinPoint);
  }

  @Test
  public void shouldDelegateProceedMethod() throws Throwable {
    frodoJoinPoint.proceed();

    proceedingJoinPoint.assertProceedMethodCalled();
  }

  @Test
  public void shouldDelegateProceedMethodWithArgs() throws Throwable {
    Object[] args = new Object[] { 1, 2, 3 };

    frodoJoinPoint.proceed(args);

    proceedingJoinPoint.assertProceedMethodCalledWithArgs(args);
  }

}
