package com.fernandocejas.example.frodo2;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.fernandocejas.example.frodo2.sample.AndroidSamples;

public class SamplesActivity extends Activity {

  private Button btnRxLogFlowable;
  private Button btnRxLogObservable;
  private Button btnRxLogSingle;
  private Button btnRxLogMaybe;
  private Button btnRxLogCompletable;

  private final AndroidSamples samples = new AndroidSamples();

  private View.OnClickListener rxLogFlowableListener = new View.OnClickListener() {
    @Override public void onClick(View v) {
      toastMessage("Running FLOWABLE Samples: Check LOGCAT!");
      samples.runFlowableExamples();
    }
  };

  private View.OnClickListener rxLogObservableListener = new View.OnClickListener() {
    @Override public void onClick(View v) {
      toastMessage("Running OBSERVABLE Samples: Check LOGCAT!");
      samples.runObservableExamples();
    }
  };

  private View.OnClickListener rxLogSingleListener = new View.OnClickListener() {
    @Override public void onClick(View v) {
      toastMessage("Running SINGLE Samples: Check LOGCAT!");
      samples.runSingleExamples();
    }
  };

  private View.OnClickListener rxLogMaybeListener = new View.OnClickListener() {
    @Override public void onClick(View v) {
      toastMessage("Running MAYBE Samples: Check LOGCAT!");
      samples.runMaybeExamples();
    }
  };

  private View.OnClickListener rxLogCompletableListener = new View.OnClickListener() {
    @Override public void onClick(View v) {
      toastMessage("Running COMPLETABLE Samples: Check LOGCAT!");
      samples.runCompletableExamples();
    }
  };

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_samples);
    this.initialize();
  }

  @Override
  protected void onDestroy() {
    samples.dispose();
    super.onDestroy();
  }

  private void initialize() {
    this.btnRxLogFlowable = findViewById(R.id.btnRxLogFlowable);
    this.btnRxLogObservable = findViewById(R.id.btnRxLogObservable);
    this.btnRxLogSingle = findViewById(R.id.btnRxLogSingle);
    this.btnRxLogMaybe = findViewById(R.id.btnRxLogMaybe);
    this.btnRxLogCompletable = findViewById(R.id.btnRxLogCompletable);

    this.btnRxLogFlowable.setOnClickListener(rxLogFlowableListener);
    this.btnRxLogObservable.setOnClickListener(rxLogObservableListener);
    this.btnRxLogSingle.setOnClickListener(rxLogSingleListener);
    this.btnRxLogMaybe.setOnClickListener(rxLogMaybeListener);
    this.btnRxLogCompletable.setOnClickListener(rxLogCompletableListener);
  }

  private void toastMessage(String message) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
  }
}
