package com.fernandocejas.frodo2.logger.observable;

import com.fernandocejas.frodo2.logger.logging.MessageManager;
import com.fernandocejas.frodo2.test.UnitTest;
import io.reactivex.observers.TestObserver;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;

@SuppressWarnings("unchecked")
public class FrodoForObservableTest extends UnitTest {

  @Rule public ObservableRule observableRule = new ObservableRule(this.getClass());

  private FrodoForObservable frodoObservable;
  private TestObserver observer;

  @Mock private MessageManager messageManager;

  @Before
  public void setUp() {
    frodoObservable = new FrodoForObservable(observableRule.joinPoint(), messageManager);
    observer = new TestObserver();
  }

  @Test
  public void shouldBuildObservable() throws Throwable {
    frodoObservable.observable().subscribe(observer);

    observer.assertResult(ObservableRule.OBSERVABLE_STREAM_VALUE);
    observer.assertNoErrors();
    observer.assertComplete();
  }

  @Test
  public void shouldPrintObservableInfo() throws Throwable {
    frodoObservable.observable();

    verify(messageManager).printObservableInfo(any(ObservableInfo.class));
  }

  @Test
  public void shouldLogObservableInformation() throws Throwable {
    frodoObservable.observable().subscribe(observer);

    verify(messageManager).printObservableInfo(any(ObservableInfo.class));
    verify(messageManager).printObservableOnSubscribe(any(ObservableInfo.class));
    verify(messageManager).printObservableOnNextWithValue(any(ObservableInfo.class), any());
    verify(messageManager).printObservableOnCompleted(any(ObservableInfo.class));
    verify(messageManager).printObservableOnTerminate(any(ObservableInfo.class));
    verify(messageManager).printObservableItemTimeInfo(any(ObservableInfo.class));
    verify(messageManager).printObservableThreadInfo(any(ObservableInfo.class));
    verify(messageManager).printObservableOnUnsubscribe(any(ObservableInfo.class));
  }
}
