package com.fernandocejas.example.frodo2.sample;

import com.fernandocejas.frodo2.annotation.RxLogObservable;
import io.reactivex.Observable;

public class ObservableSample {

  public ObservableSample() {}

  @RxLogObservable
  public Observable<Integer> numbers() {
    return Observable.just(1, 2);
  }

  @RxLogObservable
  public Observable<String> strings() {
    return Observable.just("Hello", "My", "Name", "Is", "Fernando");
  }
}
