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

import io.reactivex.Observable;
import io.reactivex.observers.DisposableObserver;

class ObservableSample {

  ObservableSample() {}

  void execute() {
    final Observable<String> observable = Observable.just("Silvia", "Yanina", "Fernando");
    observable.subscribeWith(new MyObserver());
  }

  private static class MyObserver extends DisposableObserver<String> {

    @Override public void onNext(String value) {
      System.out.println(this.getClass().getSimpleName() + ": item ---> " + value);
    }

    @Override public void onError(Throwable e) {
      //no op
    }

    @Override public void onComplete() {
      //no op
    }
  }
}
