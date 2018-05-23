package com.fernandocejas.example.frodo2;

import com.fernandocejas.example.frodo2.ObservableSamples.MyDummyClass;
import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import java.util.List;
import java.util.concurrent.TimeUnit;

class JavaSamples {

  private final FlowableSamples flowableSamples;
  private final ObservableSamples observableSamples;
  private final SingleSamples singleSamples;
  private final MaybeSamples maybeSamples;
  private final CompletableSamples completableSamples;

  private final CompositeDisposable disposables;

  JavaSamples() {
    flowableSamples = new FlowableSamples();
    observableSamples = new ObservableSamples();
    singleSamples = new SingleSamples();
    maybeSamples = new MaybeSamples();
    completableSamples = new CompletableSamples();

    disposables = new CompositeDisposable();
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

  void runSingleExamples() {
    executeRxSingleSamples();
  }

  void runMaybeExamples() {
    executeRxMaybeSamples();
  }

  void runCompletableExamples() {
    executeRxCompletableSamples();
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
  private void executeRxSingleSamples() {
    final Single<Integer> integer =
        singleSamples.number().subscribeOn(Schedulers.newThread());
    addDisposable(integer.subscribe());

    final Single<String> string = singleSamples.string()
        .delay(2, TimeUnit.SECONDS)
        .subscribeOn(Schedulers.io())
        .observeOn(Schedulers.newThread());
    disposables.add(string.subscribe());

    final Single<SingleSamples.MyClass> single =
        singleSamples.singleFromObservable().delay(8, TimeUnit.SECONDS).subscribeOn(Schedulers.io());
    disposables.add(single.subscribe());
  }

  //------------------------------------------------
  // M A Y B E      S A M P L E S
  //------------------------------------------------
  private void executeRxMaybeSamples() {
    final Maybe<Integer> integer =
        maybeSamples.number().subscribeOn(Schedulers.newThread());
    addDisposable(integer.subscribe());

    final Maybe<String> string = maybeSamples.string()
        .delay(2, TimeUnit.SECONDS)
        .subscribeOn(Schedulers.io())
        .observeOn(Schedulers.newThread());
    disposables.add(string.subscribe());

    final Maybe<MaybeSamples.MyClass> maybe =
        maybeSamples.maybeFromSingle().delay(8, TimeUnit.SECONDS).subscribeOn(Schedulers.io());
    disposables.add(maybe.subscribe());
  }

  //------------------------------------------------
  // C O M P L E T A B L E      S A M P L E S
  //------------------------------------------------
  private void executeRxCompletableSamples() {
    final Completable doSomething = completableSamples.doSomething()
        .subscribeOn(Schedulers.newThread())
        .observeOn(Schedulers.io());
    disposables.add(doSomething.subscribe());

    final Completable executeOperation = completableSamples.executeOperation()
        .subscribeOn(Schedulers.single())
        .observeOn(Schedulers.single());
    disposables.add(executeOperation.subscribe());
  }
}
