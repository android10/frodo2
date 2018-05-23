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

import com.fernandocejas.frodo2.annotation.RxLogObservable;
import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.schedulers.Schedulers;
import java.util.Arrays;
import java.util.List;

class ObservableSamples {

  ObservableSamples() {}

  @RxLogObservable
  Observable<Integer> numbers() {
    return Observable.just(1, 2, 3, 4);
  }

  @RxLogObservable
  Observable<String> strings() {
    return Observable.just("Hello", "My", "Name", "Is", "Fernando");
  }

  @RxLogObservable
  Observable<String> error() {
    return Observable.error(new IllegalArgumentException("My error"));
  }

  @RxLogObservable
  Observable<String> stringItemWithDefer() {
    return Observable.defer(() -> Observable.create((ObservableOnSubscribe<String>) emitter -> {
      try {
        emitter.onNext("String item value");
        emitter.onComplete();
      } catch (Exception e) {
        emitter.onError(e);
      }
    }));
  }

  @RxLogObservable
  Observable<String> manualCreation() {
    return Observable.create((ObservableOnSubscribe<String>) emitter -> {
      try {
        emitter.onNext("String value emitted!");
        emitter.onComplete();
      } catch (Exception e) {
        emitter.onError(e);
      }
    }).subscribeOn(Schedulers.newThread()).observeOn(Schedulers.single());
  }

  @RxLogObservable
  Observable<Void> doNothing() {
    return Observable.empty();
  }

  @RxLogObservable
  Observable<MyDummyClass> doSomething() {
    return Observable.just(new MyDummyClass("Fernando"));
  }

  @RxLogObservable
  Observable<List<MyDummyClass>> list() {
    return Observable.just(buildDummyList());
  }

  private List<MyDummyClass> buildDummyList() {
    return Arrays.asList(new MyDummyClass("Batman"), new MyDummyClass("Superman"));
  }

  public static final class MyDummyClass {
    private final String name;

    MyDummyClass(String name) {
      this.name = name;
    }

    @Override
    public String toString() {
      return "Name: " + name;
    }
  }
}
