package com.fernandocejas.frodo2.logger.maybe;

import com.fernandocejas.frodo2.logging.Logger;
import com.fernandocejas.frodo2.test.TestJoinPoint;
import com.fernandocejas.frodo2.test.TestProceedingJoinPoint;
import com.fernandocejas.frodo2.test.UnitTest;

import org.junit.Before;
import org.junit.Test;

import io.reactivex.Maybe;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

public class MaybeWeaverTest extends UnitTest {

  private MaybeWeaver maybeWeaver;

  private TestProceedingJoinPoint proceedingJoinPoint;

  @Before
  public void setUp() {
    maybeWeaver = new MaybeWeaver();
    TestJoinPoint testJoinPoint =
        new TestJoinPoint.Builder(this.getClass()).withReturnType(Maybe.class).build();
    proceedingJoinPoint = new TestProceedingJoinPoint(testJoinPoint);
  }

  @Test
  @SuppressWarnings("AccessStaticViaInstance")
  public void shouldGetCorrectReturnType() {
    final boolean result = maybeWeaver.methodAnnotatedWithRxLogMaybe(proceedingJoinPoint);
    assertThat(result).isTrue();
  }

  @Test
  public void shouldWeaveAroundJoinPointAndReturnCorrectObject() throws Throwable {
    final Object object = maybeWeaver.weaveAroundJoinPoint(proceedingJoinPoint, mock(Logger.class));
    assertThat(object).isInstanceOf(Maybe.class);
  }
}
