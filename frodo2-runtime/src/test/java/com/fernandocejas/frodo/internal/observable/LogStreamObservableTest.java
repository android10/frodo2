package com.fernandocejas.frodo.internal.observable;

import com.fernandocejas.frodo.core.optional.Optional;
import com.fernandocejas.frodo.internal.MessageManager;
import io.reactivex.observers.TestObserver;
import io.reactivex.schedulers.TestScheduler;
import java.util.concurrent.TimeUnit;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@SuppressWarnings("unchecked") @RunWith(MockitoJUnitRunner.class)
public class LogStreamObservableTest {

  @Rule public ObservableRule observableRule = new ObservableRule(this.getClass());

  private LogStreamObservable loggableObservable;
  private TestObserver observer;

  @Mock private MessageManager messageManager;

  @Before
  public void setUp() {
    observer = new TestObserver();
    loggableObservable =
        new LogStreamObservable(observableRule.joinPoint(), messageManager, observableRule.info());
  }

  @Test
  public void shouldLogOnlyStreamData() throws Throwable {
    loggableObservable.get(observableRule.stringType()).subscribe(observer);

    verify(messageManager).printObservableOnNextWithValue(any(ObservableInfo.class), anyString());
    verify(messageManager).printObservableItemTimeInfo(any(ObservableInfo.class));
    verifyNoMoreInteractions(messageManager);
  }

  @Test
  public void shouldFillInObservableItemsInfo() throws Throwable {
    final TestScheduler testScheduler = new TestScheduler();
    loggableObservable.get(observableRule.stringType())
        .delay(2, TimeUnit.SECONDS)
        .subscribeOn(testScheduler)
        .subscribe(observer);

    testScheduler.advanceTimeBy(1, TimeUnit.SECONDS);
    testScheduler.advanceTimeBy(2, TimeUnit.SECONDS);

    final ObservableInfo observableInfo = loggableObservable.getInfo();
    final Optional<Integer> totalEmittedItems = observableInfo.getTotalEmittedItems();
    final Optional<Long> totalExecutionTime = observableInfo.getTotalExecutionTime();

    //TODO: check total execution time
    assertThat(totalEmittedItems.isPresent()).isTrue();
    assertThat(totalEmittedItems.get()).isEqualTo(1);
    //assertThat(totalExecutionTime.isPresent()).isTrue();
  }
}
