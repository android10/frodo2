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

import com.fernandocejas.frodo2.annotation.RxLogSingle;
import io.reactivex.Observable;
import io.reactivex.Single;

class SingleSamples {

  SingleSamples() {}

  @RxLogSingle
  Single<Integer> number() {
    return Single.just(1);
  }

  @RxLogSingle
  Single<String> string() {
    return Single.just("My Value");
  }

  @RxLogSingle
  Single<MyClass> singleFromObservable() {
    final Observable<MyClass> observable = Observable.just(new MyClass("Hello World"));
    return Single.fromObservable(observable);
  }

  static final class MyClass {
    private final String name;

    MyClass(String name) {
      this.name = name;
    }

    @Override
    public String toString() {
      return "Name: " + name;
    }
  }
}
