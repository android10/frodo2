package com.fernandocejas.example.frodo.sample;

import com.fernandocejas.frodo.annotation.RxLogObservable;
import io.reactivex.Observable;
import java.util.Arrays;
import java.util.List;

public class ObservableSample {
  public ObservableSample() {
  }

  //@RxLogObservable(EVERYTHING)
  @RxLogObservable
  public Observable<Integer> numbers() {
    return Observable.just(1, 2);
  }

  //@RxLogObservable
  public Observable<Integer> moreNumbers() {
    return Observable.just(1, 2, 3, 4);
  }

  //@RxLogObservable(STREAM)
  public Observable<String> names() {
    return Observable.just("Fernando", "Silvia");
  }

  //@RxLogObservable
  public Observable<String> error() {
    return Observable.error(new IllegalArgumentException("My error"));
  }

  //@RxLogObservable(SCHEDULERS)
  public Observable<List<MyDummyClass>> list() {
    return Observable.just(buildDummyList());
  }

  /**
   * Nothing should happen here when annotating this method with RxLogObservable
   * because it does not returns an {@link Observable}.
   */
  //@RxLogObservable(NOTHING)
  public List<MyDummyClass> buildDummyList() {
    return Arrays.asList(new MyDummyClass("Batman"), new MyDummyClass("Superman"));
  }

  //@RxLogObservable
  public Observable<String> strings() {
    return Observable.just("Hello", "My", "Name", "Is", "Fernando");
  }

  public Observable<String> stringsWithError() {
    return Observable.error(new IllegalArgumentException("My Subscriber error"));
  }

  //@RxLogObservable
  public Observable<Void> doNothing() {
    return Observable.empty();
  }

  public static final class MyDummyClass {
    private final String name;

    MyDummyClass(String name) {
      this.name = name;
    }

    @Override
    public String toString() {
      return "Name: " + name;
    }
  }
}
