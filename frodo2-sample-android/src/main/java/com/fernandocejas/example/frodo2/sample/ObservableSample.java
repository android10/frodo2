package com.fernandocejas.example.frodo2.sample;

import com.fernandocejas.frodo2.annotation.RxLogObservable;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ObservableSample {

  public ObservableSample() {}

  @RxLogObservable
  public Observable<Integer> numbers() {
    return Observable.just(1, 2, 3, 4)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread());
  }

  @RxLogObservable
  public Observable<String> strings() {
    return Observable.just("Hello", "My", "Name", "Is", "Fernando");
  }

  @RxLogObservable
  public Observable<String> error() {
    return Observable.error(new IllegalArgumentException("My error"));
  }

  @RxLogObservable
  public Observable<String> stringItemWithDefer() {
    return Observable.defer(new Callable<ObservableSource<? extends String>>() {
      @Override public ObservableSource<? extends String> call() throws Exception {
        return Observable.create(new ObservableOnSubscribe<String>() {
          @Override public void subscribe(ObservableEmitter<String> emitter) throws Exception {
            try {
              emitter.onNext("String item value");
              emitter.onComplete();
            } catch (Exception e) {
              emitter.onError(e);
            }
          }
        }).subscribeOn(Schedulers.computation());
      }
    });
  }

  @RxLogObservable
  public Observable<Void> doNothing() {
    return Observable.empty();
  }

  @RxLogObservable
  public Observable<MyDummyClass> doSomething() {
    return Observable.just(new MyDummyClass("Fernando"));
  }

  @RxLogObservable
  public Observable<List<MyDummyClass>> list() {
    return Observable.just(buildDummyList());
  }

  private List<MyDummyClass> buildDummyList() {
    return Arrays.asList(new MyDummyClass("Batman"), new MyDummyClass("Superman"));
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
