package com.fernandocejas.frodo2.logger.joinpoint;

import com.fernandocejas.frodo2.test.UnitTest;
import org.junit.Test;
import org.mockito.Mock;

import static org.assertj.core.api.Assertions.assertThat;

public class RxComponentInfoTest extends UnitTest {
  private RxComponentInfo rxComponentInfo;

  @Mock private FrodoJoinPoint frodoJoinPoint;

  @Test
  public void shouldReturnFlowableStringLiteral() {
    rxComponentInfo = new RxComponentInfo(RxComponent.FLOWABLE, frodoJoinPoint);
    assertThat(rxComponentInfo.rxComponentName()).isEqualTo("Flowable");
  }

  @Test
  public void shouldReturnObservableStringLiteral() {
    rxComponentInfo = new RxComponentInfo(RxComponent.OBSERVABLE, frodoJoinPoint);
    assertThat(rxComponentInfo.rxComponentName()).isEqualTo("Observable");
  }

  @Test
  public void shouldReturnSingleStringLiteral() {
    rxComponentInfo = new RxComponentInfo(RxComponent.SINGLE, frodoJoinPoint);
    assertThat(rxComponentInfo.rxComponentName()).isEqualTo("Single");
  }
}