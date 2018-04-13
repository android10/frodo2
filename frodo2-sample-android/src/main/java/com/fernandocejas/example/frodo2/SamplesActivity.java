package com.fernandocejas.example.frodo2;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.fernandocejas.example.frodo2.sample.MyObserver;
import com.fernandocejas.example.frodo2.sample.ObservableSample;
import com.fernandocejas.example.frodo2.sample.ObservableSample.MyDummyClass;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class SamplesActivity extends Activity {

  private Button btnRxLogFlowable;
  private Button btnRxLogObservable;
  private Button btnRxLogSingle;

  private CompositeDisposable disposables = new CompositeDisposable();

  private final ObservableSample observableSample = new ObservableSample();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_samples);
    this.initialize();
  }

  @Override
  protected void onDestroy() {
    disposables.dispose();
    super.onDestroy();
  }

  private void initialize() {
    this.btnRxLogFlowable = findViewById(R.id.btnRxLogFlowable);
    this.btnRxLogObservable = findViewById(R.id.btnRxLogObservable);
    this.btnRxLogSingle = findViewById(R.id.btnRxLogSingle);

    this.btnRxLogObservable.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        executeRxObservableSampleOne();
//                executeRxObservableSampleTwo();
      }
    });
  }

  private void toastMessage(String message) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
  }

  private void addDisposable(Disposable disposable) {
    if (disposables != null) {
      disposables.add(disposable);
    }
  }

  private void executeRxObservableSampleOne() {
    final Observable<Integer> integers = observableSample.numbers();
    addDisposable(integers.subscribe());

//
//        final Observable<String> strings = observableSample.strings()
//                .delay(2, TimeUnit.SECONDS)
//                .subscribeOn(Schedulers.io())
//                .observeOn(Schedulers.newThread());
//        addDisposable(strings.subscribe());
//
//        final Observable<String> error = observableSample.error()
//                .delay(4, TimeUnit.SECONDS)
//                .subscribeOn(Schedulers.newThread())
//                .observeOn(AndroidSchedulers.mainThread());
//        addDisposable(error.subscribeWith(new MyObserver<>()));
  }

  private void executeRxObservableSampleTwo() {
    final Observable<String> stringWithDefer = observableSample.stringItemWithDefer()
            .delay(6, TimeUnit.SECONDS)
            .subscribeOn(Schedulers.single())
            .observeOn(Schedulers.computation());
    disposables.add(stringWithDefer.subscribeWith(new MyObserver<>()));

    final Observable<Void> voidObservable = observableSample.doNothing()
            .delay(8, TimeUnit.SECONDS)
            .subscribeOn(Schedulers.io());
    disposables.add(voidObservable.subscribeWith(new MyObserver<>()));

    final Observable<MyDummyClass> dummyClassObservable = observableSample.doSomething()
            .delay(10, TimeUnit.SECONDS)
            .subscribeOn(AndroidSchedulers.mainThread())
            .observeOn(AndroidSchedulers.mainThread());
    disposables.add(dummyClassObservable.subscribe());

    final Observable<List<MyDummyClass>> listObservable = observableSample.list()
            .delay(12, TimeUnit.SECONDS)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread());
    disposables.add(listObservable.subscribe());
  }
}
