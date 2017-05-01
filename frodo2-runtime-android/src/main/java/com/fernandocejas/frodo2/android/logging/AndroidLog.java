package com.fernandocejas.frodo2.android.logging;

import android.util.Log;
import com.fernandocejas.frodo2.logger.logging.Logger;

/**
 * Wrapper around {@link android.util.Log}
 */
public class AndroidLog implements Logger {

  public AndroidLog() {}

  public void log(String tag, String message) {
    Log.d(tag, message);
  }
}
