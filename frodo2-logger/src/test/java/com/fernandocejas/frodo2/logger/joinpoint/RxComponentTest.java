package com.fernandocejas.frodo2.logger.joinpoint;

import com.fernandocejas.frodo2.test.UnitTest;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class RxComponentTest extends UnitTest {
  private static final int NUMBER_RX_COMPONENTS_SUPPORTED = 5;

  @Test
  public void shouldReturnNumberRxComponentsSupported() {
    assertThat(RxComponent.values().length).isEqualTo(NUMBER_RX_COMPONENTS_SUPPORTED);
  }

  @Test
  public void shouldReturnFlowableStringLiteral() {
    final String flowableName = RxComponent.FLOWABLE.toString();
    assertThat(flowableName).isEqualTo("Flowable");
  }

  @Test
  public void shouldReturnObservableStringLiteral() {
    final String observableName = RxComponent.OBSERVABLE.toString();
    assertThat(observableName).isEqualTo("Observable");
  }

  @Test
  public void shouldReturnSingleStringLiteral() {
    final String singleName = RxComponent.SINGLE.toString();
    assertThat(singleName).isEqualTo("Single");
  }

  @Test
  public void shouldReturnMaybeStringLiteral() {
    final String singleName = RxComponent.MAYBE.toString();
    assertThat(singleName).isEqualTo("Maybe");
  }

  @Test
  public void shouldReturnCompletableStringLiteral() {
    final String singleName = RxComponent.COMPLETABLE.toString();
    assertThat(singleName).isEqualTo("Completable");
  }
}