package com.fernandocejas.example.frodo2.sample;

import io.reactivex.subscribers.ResourceSubscriber;

public class MySubscriber<T> extends ResourceSubscriber<T> {
  @Override public void onNext(T value) {}
  @Override public void onError(Throwable e) {}
  @Override public void onComplete() {}
}
