package com.fernandocejas.frodo2.logger.joinpoint;

public enum RxComponent {
  FLOWABLE,
  OBSERVABLE,
  SINGLE;

  @Override public String toString() {
    return capitalize(name());
  }

  private String capitalize(String string) {
    String lowerCase = string.toLowerCase();
    return lowerCase.substring(0, 1).toUpperCase() + lowerCase.substring(1);
  }
}
