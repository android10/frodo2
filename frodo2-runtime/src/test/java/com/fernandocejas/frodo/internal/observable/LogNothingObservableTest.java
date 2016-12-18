package com.fernandocejas.frodo.internal.observable;

import com.fernandocejas.frodo.internal.MessageManager;
import io.reactivex.observers.TestObserver;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.verifyZeroInteractions;

@SuppressWarnings("unchecked") @RunWith(MockitoJUnitRunner.class)
public class LogNothingObservableTest {

  @Rule public ObservableRule observableRule = new ObservableRule(this.getClass());

  private LogNothingObservable loggableObservable;
  private TestObserver observer;

  @Mock private MessageManager messageManager;

  @Before
  public void setUp() {
    observer = new TestObserver();
    loggableObservable = new LogNothingObservable(observableRule.joinPoint(), messageManager,
        observableRule.info());
  }

  @Test
  public void shouldNotLogAnything() throws Throwable {
    loggableObservable.get(observableRule.stringType()).subscribe(observer);

    observer.assertNoErrors();
    observer.assertComplete();
    verifyZeroInteractions(messageManager);
  }
}
