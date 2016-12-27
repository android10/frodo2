package com.fernandocejas.frodo2.internal.observable;

import com.fernandocejas.frodo2.android.internal.MessageManager;
import com.fernandocejas.frodo2.android.internal.observable.FrodoObservable;
import com.fernandocejas.frodo2.android.internal.observable.ObservableInfo;
import io.reactivex.observers.TestObserver;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;

@SuppressWarnings("unchecked")
@RunWith(MockitoJUnitRunner.class)
public class FrodoObservableTest {

  @Rule public ObservableRule observableRule = new ObservableRule(this.getClass());

  private FrodoObservable frodoObservable;
  private TestObserver observer;

  @Mock private MessageManager messageManager;

  @Before
  public void setUp() {
    frodoObservable = new FrodoObservable(observableRule.joinPoint(), messageManager);
    observer = new TestObserver();
  }

  @Test
  public void shouldPrintObservableInfo() throws Throwable {
    frodoObservable.getObservable();

    verify(messageManager).printObservableInfo(any(ObservableInfo.class));
  }

  @Test
  public void shouldBuildObservable() throws Throwable {
    frodoObservable.getObservable().subscribe(observer);

    observer.assertResult(ObservableRule.OBSERVABLE_STREAM_VALUE);
    observer.assertNoErrors();
    observer.assertComplete();
  }

  @Test
  public void shouldLogObservableInformation() throws Throwable {
    frodoObservable.getObservable().subscribe(observer);

    verify(messageManager).printObservableInfo(any(ObservableInfo.class));
  }
}
