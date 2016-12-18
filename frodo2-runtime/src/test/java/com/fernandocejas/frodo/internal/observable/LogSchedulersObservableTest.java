package com.fernandocejas.frodo.internal.observable;

import com.fernandocejas.frodo.core.optional.Optional;
import com.fernandocejas.frodo.internal.MessageManager;
import io.reactivex.observers.TestObserver;
import io.reactivex.schedulers.Schedulers;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@SuppressWarnings("unchecked") @RunWith(MockitoJUnitRunner.class)
public class LogSchedulersObservableTest {

  @Rule public ObservableRule observableRule = new ObservableRule(this.getClass());

  private LogSchedulersObservable loggableObservable;
  private TestObserver observer;

  @Mock private MessageManager messageManager;

  @Before
  public void setUp() {
    observer = new TestObserver();
    loggableObservable =
        new LogSchedulersObservable(observableRule.joinPoint(), messageManager,
            observableRule.info());
  }

  @Test
  public void shouldLogOnlyObservableSchedulers() throws Throwable {
    loggableObservable.get(observableRule.stringType()).subscribe(observer);

    verify(messageManager).printObservableThreadInfo(any(ObservableInfo.class));
    verifyNoMoreInteractions(messageManager);
  }

  @Test
  public void shouldFillInObservableThreadInfo() throws Throwable {
    loggableObservable.get(observableRule.stringType())
        .subscribeOn(Schedulers.trampoline())
        .observeOn(Schedulers.trampoline())
        .subscribe(observer);

    final ObservableInfo observableInfo = loggableObservable.getInfo();
    final Optional<String> subscribeOnThread = observableInfo.getSubscribeOnThread();
    final Optional<String> observeOnThread = observableInfo.getObserveOnThread();
    final String currentThreadName = Thread.currentThread().getName();

    assertThat(subscribeOnThread.isPresent()).isTrue();
    assertThat(observeOnThread.isPresent()).isTrue();
    assertThat(subscribeOnThread.get()).isEqualTo(currentThreadName);
    assertThat(observeOnThread.get()).isEqualTo(currentThreadName);
  }
}
