package com.fernandocejas.frodo.internal.observable;

import com.fernandocejas.frodo.core.optional.Optional;
import com.fernandocejas.frodo.internal.MessageManager;
import com.fernandocejas.frodo.joinpoint.FrodoProceedingJoinPoint;
import io.reactivex.observers.TestObserver;
import io.reactivex.schedulers.Schedulers;
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

@SuppressWarnings("unchecked") @RunWith(MockitoJUnitRunner.class)
public class LogEverythingObservableTest {

  @Rule public ObservableRule observableRule = new ObservableRule(this.getClass());

  private LogEverythingObservable loggableObservable;
  private TestObserver observer;

  @Mock private MessageManager messageManager;

  @Before
  public void setUp() {
    observer = new TestObserver();
    loggableObservable = new LogEverythingObservable(observableRule.joinPoint(), messageManager,
        observableRule.info());
  }

  @Test
  public void shouldLogEverythingObservable() throws Throwable {
    loggableObservable.get(observableRule.stringType()).subscribe(observer);

    verify(messageManager).printObservableOnSubscribe(any(ObservableInfo.class));
    verify(messageManager).printObservableOnNextWithValue(any(ObservableInfo.class), anyString());
    verify(messageManager).printObservableOnCompleted(any(ObservableInfo.class));
    verify(messageManager).printObservableOnTerminate(any(ObservableInfo.class));
    verify(messageManager).printObservableItemTimeInfo(any(ObservableInfo.class));
    //TODO: https://github.com/ReactiveX/RxJava/wiki/What's-different-in-2.0#dooncanceldoondisposeunsubscribeon
    //verify(messageManager).printObservableOnUnsubscribe(any(ObservableInfo.class));
    //verify(messageManager).printObservableThreadInfo(any(ObservableInfo.class));
  }

  @Test
  public void shouldFillInObservableBasicInfo() throws Throwable {
    loggableObservable.get(observableRule.stringType()).subscribe(observer);
    final ObservableInfo observableInfo = loggableObservable.getInfo();
    final FrodoProceedingJoinPoint frodoProceedingJoinPoint = observableRule.joinPoint();

    assertThat(observableInfo.getClassSimpleName()).isEqualTo(
        frodoProceedingJoinPoint.getClassSimpleName());
    assertThat(observableInfo.getJoinPoint()).isEqualTo(frodoProceedingJoinPoint);
    assertThat(observableInfo.getMethodName()).isEqualTo(frodoProceedingJoinPoint.getMethodName());
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

    //TODO: https://github.com/ReactiveX/RxJava/wiki/What's-different-in-2.0#dooncanceldoondisposeunsubscribeon
    assertThat(subscribeOnThread.isPresent()).isTrue();
    //assertThat(observeOnThread.isPresent()).isTrue();
    assertThat(subscribeOnThread.get()).isEqualTo(currentThreadName);
    //assertThat(observeOnThread.get()).isEqualTo(currentThreadName);
  }

  @Test
  public void shouldFillInObservableItemsInfo() throws Throwable {
    loggableObservable.get(observableRule.stringType())
        .delay(2, TimeUnit.SECONDS)
        .subscribe(observer);

    final ObservableInfo observableInfo = loggableObservable.getInfo();
    final Optional<Integer> totalEmittedItems = observableInfo.getTotalEmittedItems();
    final Optional<Long> totalExecutionTime = observableInfo.getTotalExecutionTime();

    assertThat(totalEmittedItems.isPresent()).isTrue();
    assertThat(totalExecutionTime.isPresent()).isTrue();
    assertThat(totalEmittedItems.get()).isEqualTo(1);
  }
}
