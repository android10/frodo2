package com.fernandocejas.example.frodo2;

import com.fernandocejas.example.frodo2.ObservableSample.MyDummyClass;
import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import java.util.List;
import java.util.concurrent.TimeUnit;

class Samples {

  private final ObservableSample observableSample = new ObservableSample();

  private final CompositeDisposable disposables = new CompositeDisposable();

  Samples() {
    //empty
  }

  private void addDisposable(Disposable disposable) {
    disposables.add(disposable);
  }

  void executeRxObservableSampleOne() {
    final Observable<Integer> integers =
        observableSample.numbers().subscribeOn(Schedulers.newThread());
    addDisposable(integers.subscribeWith(new MyObserver<>()));

    final Observable<String> strings = observableSample.strings()
        .delay(2, TimeUnit.SECONDS)
        .subscribeOn(Schedulers.io())
        .observeOn(Schedulers.newThread());
    disposables.add(strings.subscribe());

    final Observable<String> error = observableSample.error().delay(4, TimeUnit.SECONDS);
    disposables.add(error.subscribeWith(new MyObserver<>()));
  }

  void executeRxObservableSampleTwo() {
    final Observable<Void> voidObservable =
        observableSample.doNothing().delay(8, TimeUnit.SECONDS).subscribeOn(Schedulers.io());
    disposables.add(voidObservable.subscribeWith(new MyObserver<>()));

    final Observable<MyDummyClass> dummyClassObservable = observableSample.doSomething()
        .delay(10, TimeUnit.SECONDS)
        .subscribeOn(Schedulers.io())
        .observeOn(Schedulers.io());
    disposables.add(dummyClassObservable.subscribe());
  }

  void executeRxObservableSampleThree() {
    final Observable<String> stringWithDefer = observableSample.stringItemWithDefer()
        .subscribeOn(Schedulers.newThread())
        .observeOn(Schedulers.single());
    disposables.add(stringWithDefer.subscribeWith(new MyObserver<>()));

    final Observable<String> stringObservable = observableSample.manualCreation();
    disposables.add(stringObservable.subscribe());

    final Observable<List<MyDummyClass>> listObservable = observableSample.list()
        .subscribeOn(Schedulers.newThread())
        .observeOn(Schedulers.single());
    disposables.add(listObservable.subscribe());
  }
}
