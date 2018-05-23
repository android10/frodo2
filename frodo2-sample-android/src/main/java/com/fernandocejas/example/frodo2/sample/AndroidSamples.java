package com.fernandocejas.example.frodo2.sample;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class AndroidSamples {

  private final FlowableSamples flowableSamples;
  private final ObservableSamples observableSamples;

  private final CompositeDisposable disposables;

  public AndroidSamples() {
    flowableSamples = new FlowableSamples();
    observableSamples = new ObservableSamples();

    disposables = new CompositeDisposable();
  }

  private void addDisposable(Disposable disposable) {
    disposables.add(disposable);
  }

  public void dispose() {
    if (!disposables.isDisposed()) {
      disposables.dispose();
    }
  }

  public void runFlowableExamples() {
    executeRxFlowableSampleOne();
    executeRxFlowableSampleTwo();
  }

  public void runObservableExamples() {
    executeRxObservableSampleOne();
    executeRxObservableSampleTwo();
    executeRxObservableSampleThree();
  }

  public void runSingleExamples() {

  }

  public void runMaybeExamples() {

  }

  public void runCompletableExamples() {

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
    final Observable<Integer> integers = observableSamples.numbers()
        .subscribeOn(Schedulers.newThread())
        .observeOn(AndroidSchedulers.mainThread());

    final MyObserver<Integer> observer = new MyObserver<Integer>() {
      @Override public void onNext(Integer integer) {
        //Do Nothing
      }
    };
    addDisposable(integers.subscribeWith(observer));

    final Observable<String> strings = observableSamples.strings()
        .subscribeOn(Schedulers.io())
        .observeOn(Schedulers.newThread());
    disposables.add(strings.subscribe());

    final Observable<String> error = observableSamples.error();
    disposables.add(error.subscribeWith(new MyObserver<>()));
  }

  private void executeRxObservableSampleTwo() {
    final Observable<Void> voidObservable = observableSamples.doNothing()
        .subscribeOn(Schedulers.io());
    disposables.add(voidObservable.subscribeWith(new MyObserver<>()));

    final Observable<ObservableSamples.MyDummyClass> dummyClassObservable = observableSamples.doSomething()
        .subscribeOn(AndroidSchedulers.mainThread())
        .observeOn(AndroidSchedulers.mainThread());
    disposables.add(dummyClassObservable.subscribe());
  }

  private void executeRxObservableSampleThree() {
    final Observable<String> stringWithDefer = observableSamples.stringItemWithDefer();
    disposables.add(stringWithDefer.subscribeWith(new MyObserver<>()));

    final Observable<String> stringObservable = observableSamples.manualCreation()
        .subscribeOn(Schedulers.newThread())
        .observeOn(AndroidSchedulers.mainThread());
    disposables.add(stringObservable.subscribe());

    final Observable<List<ObservableSamples.MyDummyClass>> listObservable = observableSamples.list()
        .subscribeOn(Schedulers.newThread())
        .observeOn(AndroidSchedulers.mainThread());
    disposables.add(listObservable.subscribe());
  }
}
