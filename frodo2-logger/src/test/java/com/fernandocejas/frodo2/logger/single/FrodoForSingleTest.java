package com.fernandocejas.frodo2.logger.single;

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
public class FrodoForSingleTest extends UnitTest {

  @Rule public SingleRule singleRule = new SingleRule(this.getClass());

  private FrodoForSingle frodoForSingle;
  private TestObserver observer;

  @Mock private MessageManager messageManager;

  @Before
  public void setUp() {
    frodoForSingle = new FrodoForSingle(singleRule.joinPoint(), messageManager);
    observer = new TestObserver();
  }

  @Test
  public void shouldBuild() throws Throwable {
    frodoForSingle.single().subscribe(observer);

    observer.assertValue(SingleRule.OBSERVABLE_STREAM_VALUE);
    observer.assertNoErrors();
    observer.assertComplete();
  }

  @Test
  public void shouldPrintInfo() throws Throwable {
    frodoForSingle.single();

    verify(messageManager).printRxComponentInfo(any(RxComponentInfo.class));
  }

  @Test
  public void shouldLogInformation() throws Throwable {
    frodoForSingle.single().subscribe(observer);
    observer.dispose();

    verify(messageManager).printRxComponentInfo(any(RxComponentInfo.class));
    verify(messageManager).printOnSubscribe(any(RxComponentInfo.class));
    verify(messageManager).printOnSuccessWithValue(any(RxComponentInfo.class), any());
    verify(messageManager).printItemTimeInfo(any(RxComponentInfo.class));
    verify(messageManager).printThreadInfo(any(RxComponentInfo.class));
    verify(messageManager).printOnDispose(any(RxComponentInfo.class));
  }
}
