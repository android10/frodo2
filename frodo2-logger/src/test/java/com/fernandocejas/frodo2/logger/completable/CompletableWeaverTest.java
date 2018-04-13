package com.fernandocejas.frodo2.logger.completable;

import com.fernandocejas.frodo2.logging.Logger;
import com.fernandocejas.frodo2.test.TestJoinPoint;
import com.fernandocejas.frodo2.test.TestProceedingJoinPoint;
import com.fernandocejas.frodo2.test.UnitTest;

import org.junit.Before;
import org.junit.Test;

import io.reactivex.Completable;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

public class CompletableWeaverTest extends UnitTest {

  private CompletableWeaver completableWeaver;

  private TestProceedingJoinPoint proceedingJoinPoint;

  @Before
  public void setUp() {
    completableWeaver = new CompletableWeaver();
    TestJoinPoint testJoinPoint = new TestJoinPoint.Builder(this.getClass()).withReturnType(Completable.class).build();
    proceedingJoinPoint = new TestProceedingJoinPoint(testJoinPoint);
  }

  @Test
  @SuppressWarnings("AccessStaticViaInstance")
  public void shouldGetCorrectReturnType() {
    final boolean result = completableWeaver.methodAnnotatedWithRxLogCompletable(proceedingJoinPoint);
    assertThat(result).isTrue();
  }

  @Test
  public void shouldWeaveAroundJoinPointAndReturnCorrectObject() throws Throwable {
    final Object object = completableWeaver.weaveAroundJoinPoint(proceedingJoinPoint, mock(Logger.class));
    assertThat(object).isInstanceOf(Completable.class);
  }
}
