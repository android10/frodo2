package com.fernandocejas.frodo2.aspect;

import com.fernandocejas.frodo2.joinpoint.TestJoinPoint;
import com.fernandocejas.frodo2.joinpoint.TestProceedingJoinPoint;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class LogObservableTest {

  @Test
  public void shouldNotWeaveAroundMethodReturningOtherTypeThanObservable() {
    final TestJoinPoint joinPoint = new TestJoinPoint.Builder(this.getClass(), "toString")
        .withReturnType(this.getClass()).build();
    final TestProceedingJoinPoint proceedingJoinPoint = new TestProceedingJoinPoint(joinPoint);

    assertThat(LogObservable.methodAnnotatedWithRxLogObservable(proceedingJoinPoint)).isFalse();
  }
}
