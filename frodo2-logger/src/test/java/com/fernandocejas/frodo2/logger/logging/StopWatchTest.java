package com.fernandocejas.frodo2.logger.logging;

import com.fernandocejas.frodo2.test.UnitTest;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class StopWatchTest extends UnitTest {

  private StopWatch stopWatch;

  @Before
  public void setUp() {
    stopWatch = new StopWatch();
  }

  @Test
  public void mustResetStopWatch() {
    stopWatch.reset();

    assertThat(stopWatch.getTotalTimeMillis()).isZero();
  }

  @Test
  public void mustStartStopWatch() throws InterruptedException {
    stopWatch.start();
    Thread.sleep(10);
    stopWatch.stop();

    assertThat(stopWatch.getTotalTimeMillis()).isGreaterThan(0L);
  }
}
