package com.fernandocejas.frodo2.logger.flowable;

import com.fernandocejas.frodo2.logger.joinpoint.RxComponentInfo;
import com.fernandocejas.frodo2.logger.logging.MessageManager;
import com.fernandocejas.frodo2.test.UnitTest;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;

import io.reactivex.subscribers.TestSubscriber;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;

@SuppressWarnings("unchecked")
public class FrodoForFlowableTest extends UnitTest {

  @Rule public FlowableRule flowableRule = new FlowableRule(this.getClass());

  private FrodoForFlowable frodoFlowable;
  private TestSubscriber subscriber;

  @Mock private MessageManager messageManager;

  @Before
  public void setUp() {
    frodoFlowable = new FrodoForFlowable(flowableRule.joinPoint(), messageManager);
    subscriber = new TestSubscriber();
  }

  @Test
  public void shouldBuild() throws Throwable {
    frodoFlowable.flowable().subscribe(subscriber);

    subscriber.assertValue(FlowableRule.OBSERVABLE_STREAM_VALUE);
    subscriber.assertNoErrors();
    subscriber.assertComplete();
  }

  @Test
  public void shouldPrintInfo() throws Throwable {
    frodoFlowable.flowable();

    verify(messageManager).printRxComponentInfo(any(RxComponentInfo.class));
  }

  @Test
  public void shouldLogInformation() throws Throwable {
    frodoFlowable.flowable().subscribe(subscriber);
    subscriber.dispose();

    verify(messageManager).printRxComponentInfo(any(RxComponentInfo.class));
    verify(messageManager).printOnSubscribe(any(RxComponentInfo.class));
    verify(messageManager).printOnNextWithValue(any(RxComponentInfo.class), any());
    verify(messageManager).printOnComplete(any(RxComponentInfo.class));
    verify(messageManager).printOnTerminate(any(RxComponentInfo.class));
    verify(messageManager).printItemTimeInfo(any(RxComponentInfo.class));
    verify(messageManager).printThreadInfo(any(RxComponentInfo.class));
    verify(messageManager).printOnCancel(any(RxComponentInfo.class));
  }
}
