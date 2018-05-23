/**
 * Copyright (C) 2016 android10.org Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.fernandocejas.example.frodo2;

import java.io.BufferedReader;
import java.io.Console;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
  public static void main(String[] args) throws IOException {
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    final Samples samples = new Samples();

    System.out.println("Choose option: ");
    System.out.println("1 - FLOWABLE Samples");
    System.out.println("2 - OBSERVABLE Samples");
    System.out.println("3 - SINGLE Samples");
    System.out.println("4 - MAYBE Samples");
    System.out.println("5 - COMPLETABLE Samples");

    final String input = reader.readLine();
    switch (input) {
      case "1": samples.runFlowableExamples();
      case "2": samples.runObservableExamples();
      case "3": samples.runSingleExamples();
      case "4": samples.runMaybeExamples();
      case "5": samples.runCompletableExamples();
      default: System.exit(0);
    }
  }
}
