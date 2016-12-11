package com.fernandocejas.example.frodo.sample;

import io.reactivex.subscribers.DisposableSubscriber;

//@RxLogSubscriber
public class MySubscriber extends DisposableSubscriber<String> {
  @Override
  public void onNext(String value) {
    //empty
  }

  @Override
  public void onError(Throwable throwable) {
    //empty
  }

  @Override
  public void onComplete() {
    //empty
  }
}
