package com.fernandocejas.example.frodo.sample;

import io.reactivex.subscribers.DisposableSubscriber;

//@RxLogSubscriber
public class MySubscriberVoid extends DisposableSubscriber<Void> {
  @Override public void onComplete() {
    //empty
  }

  @Override public void onError(Throwable e) {
    //empty
  }

  @Override public void onNext(Void aVoid) {
    //empty
  }
}
