package com.fernandocejas.frodo2.test;

import org.junit.Rule;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runner.RunWith;
import org.junit.runners.model.Statement;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public abstract class UnitTest {

  @Rule public TestRule injectMocksRule = new TestRule() {
    @Override
    public Statement apply(Statement base, Description description) {
      MockitoAnnotations.initMocks(UnitTest.this);
      return base;
    }
  };
}
