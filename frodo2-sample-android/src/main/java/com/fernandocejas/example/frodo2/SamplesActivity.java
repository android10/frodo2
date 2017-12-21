package com.fernandocejas.example.frodo2;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.fernandocejas.example.frodo2.sample.MyObserver;
import com.fernandocejas.example.frodo2.sample.ObservableSample;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class SamplesActivity extends Activity {

  private Button btnRxLogFlowable;
  private Button btnRxLogObservable;
  private Button getBtnRxLogSingle;

  private CompositeDisposable disposables;

  private View.OnClickListener rxLogObservableListener = new View.OnClickListener() {
    @Override
    public void onClick(View v) {
    final ObservableSample observableSample = new ObservableSample();
    executeRxObservableSample(observableSample);
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

  private void executeRxObservableSample(ObservableSample observableSample) {
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
