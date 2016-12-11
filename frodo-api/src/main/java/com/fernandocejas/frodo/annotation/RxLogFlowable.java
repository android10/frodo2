package com.fernandocejas.frodo.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Retention(RUNTIME)
@Target({ METHOD })
public @interface RxLogFlowable {
  Scope value() default Scope.EVERYTHING;

  enum Scope { EVERYTHING, STREAM, SCHEDULERS, EVENTS, NOTHING }
}
