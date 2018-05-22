package com.fernandocejas.frodo2.logger.maybe;

import com.fernandocejas.frodo2.logger.joinpoint.RxComponentInfo;
import com.fernandocejas.frodo2.logger.logging.MessageManager;
import com.fernandocejas.frodo2.test.UnitTest;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;

import io.reactivex.observers.TestObserver;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;

@SuppressWarnings("unchecked")
public class FrodoForMaybeTest extends UnitTest {

  @Rule public MaybeRule maybeRule = new MaybeRule(this.getClass());

  private FrodoForMaybe frodoForMaybe;
  private TestObserver observer;

  @Mock private MessageManager messageManager;

  @Before
  public void setUp() {
    frodoForMaybe = new FrodoForMaybe(maybeRule.joinPoint(), messageManager);
    observer = new TestObserver();
  }

  @Test
  public void shouldBuild() throws Throwable {
    frodoForMaybe.maybe().subscribe(observer);

    observer.assertResult(MaybeRule.OBSERVABLE_STREAM_VALUE);
    observer.assertNoErrors();
    observer.assertComplete();
  }

  @Test
  public void shouldPrintInfo() throws Throwable {
    frodoForMaybe.maybe();

    verify(messageManager).printRxComponentInfo(any(RxComponentInfo.class));
  }

  @Test
  public void shouldLogInformation() throws Throwable {
    frodoForMaybe.maybe().subscribe(observer);
    observer.dispose();

    verify(messageManager).printRxComponentInfo(any(RxComponentInfo.class));
    verify(messageManager).printOnSubscribe(any(RxComponentInfo.class));
    verify(messageManager).printOnSuccessWithValue(any(RxComponentInfo.class), any());
    verify(messageManager).printItemTimeInfo(any(RxComponentInfo.class));
    verify(messageManager).printThreadInfo(any(RxComponentInfo.class));
    verify(messageManager).printOnDispose(any(RxComponentInfo.class));
  }
}
