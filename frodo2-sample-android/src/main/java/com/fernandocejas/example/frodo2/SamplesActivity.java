package com.fernandocejas.example.frodo2;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.fernandocejas.example.frodo2.sample.MyObserver;
import com.fernandocejas.example.frodo2.sample.ObservableSample;
import com.fernandocejas.example.frodo2.sample.ObservableSample.MyDummyClass;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class SamplesActivity extends Activity {

  private Button btnRxLogFlowable;
  private Button btnRxLogObservable;
  private Button getBtnRxLogSingle;

  private CompositeDisposable disposables;

  private final ObservableSample observableSample = new ObservableSample();

  private View.OnClickListener rxLogObservableListener = new View.OnClickListener() {
    @Override public void onClick(View v) {
      executeRxObservableSampleOne();
      executeRxObservableSampleTwo();
    }
  };

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_samples);
    this.initialize();
  }

  @Override protected void onDestroy() {
    disposables.dispose();
    super.onDestroy();
  }

  private void initialize() {
    this.disposables = new CompositeDisposable();

    this.btnRxLogFlowable = findViewById(R.id.btnRxLogFlowable);
    this.btnRxLogObservable = findViewById(R.id.btnRxLogObservable);
    this.getBtnRxLogSingle = findViewById(R.id.btnRxLogSingle);

    this.btnRxLogObservable.setOnClickListener(rxLogObservableListener);
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
    final Observable<Integer> integers = observableSample.numbers()
        .subscribeOn(Schedulers.newThread())
        .observeOn(AndroidSchedulers.mainThread());
    final MyObserver<Integer> observer = new MyObserver<Integer>() {
      @Override public void onNext(Integer integer) {
        toastMessage("onNext() Integer--> " + String.valueOf(integer));
      }
    };
    addDisposable(integers.subscribeWith(observer));


    final Observable<String> strings = observableSample.strings()
        .delay(2, TimeUnit.SECONDS)
        .subscribeOn(Schedulers.io())
        .observeOn(Schedulers.newThread());
    disposables.add(strings.subscribe());

    final Observable<String> error = observableSample.error()
        .delay(4, TimeUnit.SECONDS);
    disposables.add(error.subscribeWith(new MyObserver<>()));
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
