package com.fernandocejas.frodo2.android.logging;

import android.util.Log;

/**
 * Wrapper around {@link android.util.Log}
 */
public class AndroidLog {

  public AndroidLog() {
  }

  public void log(String tag, String message) {
    Log.d(tag, message);
  }
}
