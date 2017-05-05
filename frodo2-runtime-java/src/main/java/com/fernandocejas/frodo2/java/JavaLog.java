package com.fernandocejas.frodo2.java;

import com.fernandocejas.frodo2.logging.Logger;

class JavaLog implements Logger {

  JavaLog() {}

  @Override
  public void log(String tag, String message) {
    System.out.println(message);
  }
}
