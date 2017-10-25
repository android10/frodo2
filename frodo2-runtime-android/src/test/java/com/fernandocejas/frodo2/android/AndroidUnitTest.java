package com.fernandocejas.frodo2.android;

import org.junit.Rule;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;
import org.mockito.MockitoAnnotations;

/**
 * Base class for Robolectric tests.
 * Inherit from this class to create a test.
 */
//@RunWith(RobolectricGradleTestRunner.class)
//@Config(constants = BuildConfig.class,
//    application = ApplicationStub.class,
//    sdk = 21)
public abstract class AndroidUnitTest {
  @Rule public TestRule injectMocksRule = new TestRule() {
    @Override
    public Statement apply(Statement base, Description description) {
      MockitoAnnotations.initMocks(AndroidUnitTest.this);
      return base;
    }
  };
  //
  //protected static Context context() {
  //  return RuntimeEnvironment.application;
  //}
  //
  //protected static Resources resources() {
  //  return context().getResources();
  //}
  //
  //protected static SharedPreferences sharedPreferences(String name, int mode) {
  //  return new RoboSharedPreferences(new HashMap<String, Map<String, Object>>(), name, mode);
  //}
}
