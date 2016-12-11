package com.fernandocejas.example.frodo.sample;

import io.reactivex.subscribers.DisposableSubscriber;

//@RxLogSubscriber
public class MySubscriberBackpressure extends DisposableSubscriber<Integer> {

  @Override
  public void onStart() {
    request(40);
  }

  @Override
  public void onNext(Integer value) {
    //empty
  }

  @Override
  public void onError(Throwable throwable) {
    //empty
  }

  @Override
  public void onComplete() {
    if (!isDisposed()) {
      dispose();
    }
  }
}
