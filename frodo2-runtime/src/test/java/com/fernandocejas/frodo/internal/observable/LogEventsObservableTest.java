package com.fernandocejas.frodo.internal.observable;

import com.fernandocejas.frodo.internal.MessageManager;
import io.reactivex.observers.TestObserver;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@SuppressWarnings("unchecked") @RunWith(MockitoJUnitRunner.class)
public class LogEventsObservableTest {

  @Rule public ObservableRule observableRule = new ObservableRule(this.getClass());

  private LogEventsObservable loggableObservable;
  private TestObserver observer;

  @Mock private MessageManager messageManager;

  @Before
  public void setUp() {
    observer = new TestObserver();
    loggableObservable =
        new LogEventsObservable(observableRule.joinPoint(), messageManager, observableRule.info());
  }

  @Test
  public void shouldLogOnlyObservableEvents() throws Throwable {
    loggableObservable.get(observableRule.stringType()).subscribe(observer);

    verify(messageManager).printObservableOnSubscribe(any(ObservableInfo.class));
    verify(messageManager).printObservableOnNext(any(ObservableInfo.class));
    verify(messageManager).printObservableOnCompleted(any(ObservableInfo.class));
    verify(messageManager).printObservableOnTerminate(any(ObservableInfo.class));
    verify(messageManager).printObservableOnUnsubscribe(any(ObservableInfo.class));
    verifyNoMoreInteractions(messageManager);
  }
}
