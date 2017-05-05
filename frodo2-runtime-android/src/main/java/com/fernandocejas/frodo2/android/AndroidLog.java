package com.fernandocejas.frodo2.android;

import android.util.Log;
import com.fernandocejas.frodo2.logging.Logger;

/**
 * Wrapper around {@link android.util.Log}
 */
class AndroidLog implements Logger {

  AndroidLog() {}

  public void log(String tag, String message) {
    Log.d(tag, message);
  }
}
