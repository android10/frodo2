package com.fernandocejas.frodo2.java.logging;

import com.fernandocejas.frodo2.logger.logging.Logger;

public class JavaLog implements Logger {

  public JavaLog() {}

  @Override
  public void log(String tag, String message) {
    //TODO: implement proper logging
    System.out.println(message);
  }
}
