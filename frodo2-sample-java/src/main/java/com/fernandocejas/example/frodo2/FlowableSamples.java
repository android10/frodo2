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

import com.fernandocejas.frodo2.annotation.RxLogFlowable;
import io.reactivex.Flowable;

class FlowableSamples {

  FlowableSamples() {}

  @RxLogFlowable
  Flowable<Integer> numbers() {
    return Flowable.just(1, 2, 3, 4);
  }

  @RxLogFlowable
  Flowable<String> strings() {
    return Flowable.just("Hello", "My", "Name", "Is", "Fernando");
  }

  @RxLogFlowable
  Flowable<String> error() {
    return Flowable.error(new IllegalArgumentException("My error"));
  }

  @RxLogFlowable
  Flowable<Void> doNothing() {
    return Flowable.empty();
  }
}
