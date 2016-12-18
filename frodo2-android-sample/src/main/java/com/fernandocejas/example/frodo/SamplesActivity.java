package com.fernandocejas.example.frodo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.fernandocejas.example.frodo.sample.MyObserver;
import com.fernandocejas.example.frodo.sample.ObservableSample;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import java.util.List;

public class SamplesActivity extends Activity {

  private Button btnRxLogObservable;
  private Button btnRxLogSubscriber;

  private CompositeDisposable disposables;

  private View.OnClickListener rxLogObservableListener = new View.OnClickListener() {
    @Override
    public void onClick(View v) {
      final ObservableSample observableSample = new ObservableSample();
      executeExampleOne(observableSample);
      //executeExampleTwo(observableSample);
      //executeExampleThree(observableSample);
      //executeExampleFour(observableSample);
      //executeExampleFive(observableSample);
    }
  };

  private View.OnClickListener rxLogSubscriberListener = new View.OnClickListener() {
    @Override
    public void onClick(View v) {
      final ObservableSample observableSample = new ObservableSample();
      toastMessage("Subscribing to observables...Check logcat output...");

      observableSample.strings()
          .subscribeOn(Schedulers.newThread())
          .observeOn(AndroidSchedulers.mainThread())
          .subscribe(new MyObserver<String>());

      observableSample.stringsWithError()
          .subscribeOn(Schedulers.newThread())
          .observeOn(AndroidSchedulers.mainThread())
          .subscribe(new MyObserver<String>());

      observableSample.doNothing()
          .subscribeOn(Schedulers.newThread())
          .observeOn(AndroidSchedulers.mainThread())
          .subscribe(new MyObserver<Void>());
    }
  };

  @Override
  protected void onCreate(Bundle savedInstanceState) {
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

    this.btnRxLogObservable = (Button) findViewById(R.id.btnRxLogObservable);
    this.btnRxLogSubscriber = (Button) findViewById(R.id.btnRxLogSubscriber);

    this.btnRxLogObservable.setOnClickListener(rxLogObservableListener);
    this.btnRxLogSubscriber.setOnClickListener(rxLogSubscriberListener);
  }

  private void toastMessage(String message) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
  }

  private void addDisposable(Disposable disposable) {
    if (disposables != null) {
      disposables.add(disposable);
    }
  }

  private void executeExampleOne(ObservableSample observableSample) {
    final Observable<Integer> observable = observableSample.numbers()
        .subscribeOn(Schedulers.newThread())
        .observeOn(AndroidSchedulers.mainThread());

    final MyObserver<Integer> observer = new MyObserver<Integer>() {
      @Override public void onNext(Integer integer) {
        toastMessage("onNext() Integer--> " + String.valueOf(integer));
      }
    };

    addDisposable(observable.subscribeWith(observer));
  }

  private void executeExampleTwo(ObservableSample observableSample) {
    final Observable<String> observable = observableSample.names()
        .subscribeOn(Schedulers.newThread())
        .observeOn(AndroidSchedulers.mainThread());

    final MyObserver<String> observer = new MyObserver<String>() {
      @Override public void onNext(String string) {
        toastMessage("onNext() String--> " + string);
      }
    };

    addDisposable(observable.subscribeWith(observer));
  }

  private void executeExampleThree(ObservableSample observableSample) {
    final Observable<String> observable = observableSample.error()
        .observeOn(AndroidSchedulers.mainThread());

    final MyObserver<String> observer = new MyObserver<String>() {
      @Override public void onError(Throwable e) {
        toastMessage("onError() --> " + e.getMessage());
      }
    };

    observable.subscribeWith(observer);
  }

  private void executeExampleFour(ObservableSample observableSample) {
    final Observable<List<ObservableSample.MyDummyClass>> observable = observableSample.list()
        .subscribeOn(Schedulers.newThread())
        .observeOn(AndroidSchedulers.mainThread());

    final MyObserver<List<ObservableSample.MyDummyClass>> observer =
        new MyObserver<List<ObservableSample.MyDummyClass>>() {
          @Override public void onNext(List<ObservableSample.MyDummyClass> myDummyClasses) {
            toastMessage("onNext() List--> " + myDummyClasses.toString());
          }
        };

    observable.subscribeWith(observer);
  }

  private void executeExampleFive(ObservableSample observableSample) {
    observableSample.doNothing()
        .subscribeOn(Schedulers.newThread())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe();
  }
}
