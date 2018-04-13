package com.fernandocejas.frodo2.logger.flowable;

import com.fernandocejas.frodo2.logging.Logger;
import com.fernandocejas.frodo2.test.TestJoinPoint;
import com.fernandocejas.frodo2.test.TestProceedingJoinPoint;
import com.fernandocejas.frodo2.test.UnitTest;

import org.junit.Before;
import org.junit.Test;

import io.reactivex.Flowable;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

public class FlowableWeaverTest extends UnitTest {

  private FlowableWeaver flowableWeaver;

  private TestProceedingJoinPoint proceedingJoinPoint;

  @Before
  public void setUp() {
    flowableWeaver = new FlowableWeaver();
    TestJoinPoint testJoinPoint = new TestJoinPoint.Builder(this.getClass()).withReturnType(Flowable.class).build();
    proceedingJoinPoint = new TestProceedingJoinPoint(testJoinPoint);
  }

  @Test
  @SuppressWarnings("AccessStaticViaInstance")
  public void shouldGetCorrectReturnType() {
    final boolean result = flowableWeaver.methodAnnotatedWithRxLogFlowable(proceedingJoinPoint);
    assertThat(result).isTrue();
  }

  @Test
  public void shouldWeaveAroundJoinPointAndReturnCorrectObject() throws Throwable {
    final Object object = flowableWeaver.weaveAroundJoinPoint(proceedingJoinPoint, mock(Logger.class));
    assertThat(object).isInstanceOf(Flowable.class);
  }
}
