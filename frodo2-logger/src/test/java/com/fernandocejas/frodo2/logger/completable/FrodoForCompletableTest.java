package com.fernandocejas.frodo2.logger.completable;

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
public class FrodoForCompletableTest extends UnitTest {

  @Rule public CompletableRule completableRule = new CompletableRule(this.getClass());

  private FrodoForCompletable frodoForCompletable;
  private TestObserver observer;

  @Mock private MessageManager messageManager;

  @Before
  public void setUp() {
    frodoForCompletable = new FrodoForCompletable(completableRule.joinPoint(), messageManager);
    observer = new TestObserver();
  }

  @Test
  public void shouldBuild() throws Throwable {
    frodoForCompletable.completable().subscribe(observer);

    observer.assertNoValues();
    observer.assertNoErrors();
    observer.assertComplete();
  }

  @Test
  public void shouldPrintInfo() throws Throwable {
    frodoForCompletable.completable();

    verify(messageManager).printRxComponentInfo(any(RxComponentInfo.class));
  }

  @Test
  public void shouldLogInformation() throws Throwable {
    frodoForCompletable.completable().subscribe(observer);
    observer.dispose();

    verify(messageManager).printRxComponentInfo(any(RxComponentInfo.class));
    verify(messageManager).printOnSubscribe(any(RxComponentInfo.class));
    verify(messageManager).printOnComplete(any(RxComponentInfo.class));
    verify(messageManager).printItemTimeInfo(any(RxComponentInfo.class));
    verify(messageManager).printThreadInfo(any(RxComponentInfo.class));
    verify(messageManager).printOnDispose(any(RxComponentInfo.class));
  }
}
