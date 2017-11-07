package com.fernandocejas.example.frodo2.sample;

import io.reactivex.observers.ResourceObserver;

public class MyObserver<T> extends ResourceObserver<T> {
  @Override public void onNext(T value) {
    //empty
  }

  @Override public void onError(Throwable e) {
    //empty
  }

  @Override public void onComplete() {
    //empty
  }
}
