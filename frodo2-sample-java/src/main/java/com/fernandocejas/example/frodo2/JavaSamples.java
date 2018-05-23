package com.fernandocejas.example.frodo2;

import com.fernandocejas.example.frodo2.ObservableSamples.MyDummyClass;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import java.util.List;
import java.util.concurrent.TimeUnit;

class JavaSamples {

  private final FlowableSamples flowableSamples = new FlowableSamples();
  private final ObservableSamples observableSamples = new ObservableSamples();

  private final CompositeDisposable disposables = new CompositeDisposable();

  JavaSamples() {
    //empty
  }

  private void addDisposable(Disposable disposable) {
    disposables.add(disposable);
  }

  void runFlowableExamples() {
    executeRxFlowableSampleOne();
    executeRxFlowableSampleTwo();
  }

  void runObservableExamples() {
    executeRxObservableSampleOne();
    executeRxObservableSampleTwo();
    executeRxObservableSampleThree();
  }

  //------------------------------------------------
  // F L O W A B L E      S A M P L E S
  //------------------------------------------------
  private void executeRxFlowableSampleOne() {
    final Flowable<Integer> integers =
        flowableSamples.numbers().subscribeOn(Schedulers.newThread());
    addDisposable(integers.subscribeWith(new MySubscriber<>()));

    final Flowable<String> strings = flowableSamples.strings()
        .delay(2, TimeUnit.SECONDS)
        .subscribeOn(Schedulers.io())
        .observeOn(Schedulers.newThread());
    disposables.add(strings.subscribe());
  }

  private void executeRxFlowableSampleTwo() {
    final Flowable<Void> voidFlowable =
        flowableSamples.doNothing().delay(8, TimeUnit.SECONDS).subscribeOn(Schedulers.io());
    disposables.add(voidFlowable.subscribeWith(new MySubscriber<>()));

    final Flowable<String> error = flowableSamples.error().delay(4, TimeUnit.SECONDS);
    disposables.add(error.subscribeWith(new MySubscriber<>()));
  }

  //------------------------------------------------
  // O B S E R V A B L E      S A M P L E S
  //------------------------------------------------
  private void executeRxObservableSampleOne() {
    final Observable<Integer> integers =
        observableSamples.numbers().subscribeOn(Schedulers.newThread());
    addDisposable(integers.subscribeWith(new MyObserver<>()));

    final Observable<String> strings = observableSamples.strings()
        .delay(2, TimeUnit.SECONDS)
        .subscribeOn(Schedulers.io())
        .observeOn(Schedulers.newThread());
    disposables.add(strings.subscribe());

    final Observable<String> error = observableSamples.error().delay(4, TimeUnit.SECONDS);
    disposables.add(error.subscribeWith(new MyObserver<>()));
  }

  private void executeRxObservableSampleTwo() {
    final Observable<Void> voidObservable =
        observableSamples.doNothing().delay(8, TimeUnit.SECONDS).subscribeOn(Schedulers.io());
    disposables.add(voidObservable.subscribeWith(new MyObserver<>()));

    final Observable<MyDummyClass> dummyClassObservable = observableSamples.doSomething()
        .delay(10, TimeUnit.SECONDS)
        .subscribeOn(Schedulers.io())
        .observeOn(Schedulers.io());
    disposables.add(dummyClassObservable.subscribe());
  }

  private void executeRxObservableSampleThree() {
    final Observable<String> stringWithDefer = observableSamples.stringItemWithDefer()
        .subscribeOn(Schedulers.newThread())
        .observeOn(Schedulers.single());
    disposables.add(stringWithDefer.subscribeWith(new MyObserver<>()));

    final Observable<String> stringObservable = observableSamples.manualCreation();
    disposables.add(stringObservable.subscribe());

    final Observable<List<MyDummyClass>> listObservable = observableSamples.list()
        .subscribeOn(Schedulers.newThread())
        .observeOn(Schedulers.single());
    disposables.add(listObservable.subscribe());
  }

  //------------------------------------------------
  // S I N G L E      S A M P L E S
  //------------------------------------------------
  void runSingleExamples() {

  }

  //------------------------------------------------
  // M A Y B E      S A M P L E S
  //------------------------------------------------
  void runMaybeExamples() {

  }

  //------------------------------------------------
  // C O M P L E T A B L E      S A M P L E S
  //------------------------------------------------
  void runCompletableExamples() {

  }
}
