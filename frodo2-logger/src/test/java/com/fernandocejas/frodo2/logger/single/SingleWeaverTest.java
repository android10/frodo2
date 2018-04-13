package com.fernandocejas.frodo2.logger.single;

import com.fernandocejas.frodo2.logging.Logger;
import com.fernandocejas.frodo2.test.TestJoinPoint;
import com.fernandocejas.frodo2.test.TestProceedingJoinPoint;
import com.fernandocejas.frodo2.test.UnitTest;

import org.junit.Before;
import org.junit.Test;

import io.reactivex.Single;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

public class SingleWeaverTest extends UnitTest {

  private SingleWeaver singleWeaver;

  private TestProceedingJoinPoint proceedingJoinPoint;

  @Before
  public void setUp() {
    singleWeaver = new SingleWeaver();
    TestJoinPoint testJoinPoint = new TestJoinPoint.Builder(this.getClass()).withReturnType(Single.class).build();
    proceedingJoinPoint = new TestProceedingJoinPoint(testJoinPoint);
  }

  @Test
  @SuppressWarnings("AccessStaticViaInstance")
  public void shouldGetCorrectReturnType() {
    final boolean result = singleWeaver.methodAnnotatedWithRxLogSingle(proceedingJoinPoint);
    assertThat(result).isTrue();
  }

  @Test
  public void shouldWeaveAroundJoinPointAndReturnCorrectObject() throws Throwable {
    final Object object = singleWeaver.weaveAroundJoinPoint(proceedingJoinPoint, mock(Logger.class));
    assertThat(object).isInstanceOf(Single.class);
  }
}
