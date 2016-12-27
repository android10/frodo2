package com.fernandocejas.example.frodo2;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.fernandocejas.example.frodo.R;
import com.fernandocejas.example.frodo2.sample.MyObserver;
import com.fernandocejas.example.frodo2.sample.ObservableSample;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class SamplesActivity extends Activity {

  private Button btnRxLogObservable;
  private Button btnRxLogSubscriber;

  private CompositeDisposable disposables;

  private View.OnClickListener rxLogObservableListener = new View.OnClickListener() {
    @Override
    public void onClick(View v) {
      final ObservableSample observableSample = new ObservableSample();
      executeExampleOne(observableSample);
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
}
