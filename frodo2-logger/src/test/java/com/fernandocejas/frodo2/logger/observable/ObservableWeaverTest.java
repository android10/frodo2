package com.fernandocejas.frodo2.logger.observable;

import com.fernandocejas.frodo2.logging.Logger;
import com.fernandocejas.frodo2.test.TestJoinPoint;
import com.fernandocejas.frodo2.test.TestProceedingJoinPoint;
import com.fernandocejas.frodo2.test.UnitTest;
import io.reactivex.Observable;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

public class ObservableWeaverTest extends UnitTest {

  private ObservableWeaver observableWeaver;

  private TestProceedingJoinPoint proceedingJoinPoint;

  @Before
  public void setUp() {
    observableWeaver = new ObservableWeaver();
    TestJoinPoint testJoinPoint =
        new TestJoinPoint.Builder(this.getClass()).withReturnType(Observable.class).build();
    proceedingJoinPoint = new TestProceedingJoinPoint(testJoinPoint);
  }

  @Test
  @SuppressWarnings("AccessStaticViaInstance")
  public void shouldGetCorrectReturnType() {
    final boolean result = observableWeaver.methodAnnotatedWithRxLogObservable(proceedingJoinPoint);

    assertThat(result).isTrue();
  }

  @Test
  public void shouldWeaveAroundJoinPointAndReturnCorrectObject() throws Throwable {
    final Object object =
        observableWeaver.weaveAroundJoinPoint(proceedingJoinPoint, mock(Logger.class));

    assertThat(object).isInstanceOf(Observable.class);
  }
}
