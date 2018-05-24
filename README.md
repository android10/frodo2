

Frodo 2 [![Build Status](https://travis-ci.org/android10/frodo2.svg?branch=master)](https://travis-ci.org/android10/frodo2)
=========================

[![Hex.pm](https://img.shields.io/hexpm/l/plug.svg)](http://www.apache.org/licenses/LICENSE-2.0) 
[![Platform](https://img.shields.io/badge/platform-kotlin-blue.svg)](https://kotlinlang.org/)
[![Platform](https://img.shields.io/badge/platform-android-green.svg)](http://developer.android.com/index.html)
[![Platform](https://img.shields.io/badge/platform-java-orange.svg)](https://docs.oracle.com/javase/8/docs/)

```Frodo 2``` is the second version of [Frodo](https://github.com/android10/frodo/), mainly used to make easier debugging projects where [RxJava 2](https://github.com/ReactiveX/RxJava/wiki/What's-different-in-2.0) is used. 
Just annotated your code and voilá! 

- **On Android projects (both Kotlin and Java):** It is safe to persist any ```Frodo 2``` annotation in the codebase since the code generator will **ONLY** work on ```debug``` versions of the application where the plugin is applied.

- **On pure Kotlin/Java projects: EXPERIMENTAL,** you can keep the annotation in the source code but you have to manually enable/disable the code generation (check **Enabling Frodo 2** section).

RxJava 2 building blocks supported: 
* `Flowable<T>` 
* `Observable<T>` 
* `Single<T>` 
* `Maybe<T>` 
* `Completable`

Check **Main Features** Section below for more details.

![frodo_hug](https://cloud.githubusercontent.com/assets/1360604/10925718/e7ea4318-8290-11e5-91b4-f2bfbde65319.gif)


Main Features
=========================

- **@RxLogFlowable:** Annotated functions which return ```io.reactivex.Flowable<T>``` will print the following information on the log output:

```java
@RxLogFlowable
Flowable<Integer> numbers() {
  return Flowable.just(1, 2, 3, 4);
}
```
```
Frodo2 => [@Flowable :: @InClass -> FlowableSamples :: @Method -> numbers()]
Frodo2 => [@Flowable#numbers -> onSubscribe()]
Frodo2 => [@Flowable#numbers -> onRequest() -> 9223372036854775807]
Frodo2 => [@Flowable#numbers -> onNext() -> 1]
Frodo2 => [@Flowable#numbers -> onNext() -> 2]
Frodo2 => [@Flowable#numbers -> onNext() -> 3]
Frodo2 => [@Flowable#numbers -> onNext() -> 4]
Frodo2 => [@Flowable#numbers -> onComplete()]
Frodo2 => [@Flowable#numbers -> onTerminate()]
Frodo2 => [@Flowable#numbers -> @Emitted -> 4 elements :: @Time -> 1 ms]
Frodo2 => [@Flowable#numbers -> @ObserveOn -> RxNewThreadScheduler-3]
```

- **@RxLogObservable:** Annotated functions which return ```io.reactivex.Observable<T>``` will print the following information on the log output:

```java
@RxLogObservable
Observable<String> strings() {
  return Observable.just("Hello", "My", "Name", "Is", "Fernando");
}
```
```
Frodo2 => [@Observable :: @InClass -> ObservableSamples :: @Method -> strings()]
Frodo2 => [@Observable#strings -> onSubscribe()]
Frodo2 => [@Observable#strings -> onNext() -> Hello]
Frodo2 => [@Observable#strings -> onNext() -> My]
Frodo2 => [@Observable#strings -> onNext() -> Name]
Frodo2 => [@Observable#strings -> onNext() -> Is]
Frodo2 => [@Observable#strings -> onNext() -> Fernando]
Frodo2 => [@Observable#strings -> onComplete()]
Frodo2 => [@Observable#strings -> onTerminate()]
Frodo2 => [@Observable#strings -> @Emitted -> 5 elements :: @Time -> 9 ms]
Frodo2 => [@Observable#strings -> @ObserveOn -> RxCachedThreadScheduler-1]
```

- **@RxLogSingle:** Annotated functions which return ```io.reactivex.Single<T>``` will print the following information on the log output:

```java
@RxLogSingle
Single<String> string() {
  return Single.just("My Value");
}
```
```
Frodo2 => [@Single :: @InClass -> SingleSamples :: @Method -> string()]
Frodo2 => [@Single#string -> onSubscribe()]
Frodo2 => [@Single#string -> onSuccess() -> My Value]
Frodo2 => [@Single#string -> @Emitted -> 1 element :: @Time -> 0 ms]
Frodo2 => [@Single#string -> @ObserveOn -> RxCachedThreadScheduler-1]
```

- **@RxLogMaybe:** Annotated functions which return ```io.reactivex.Maybe<T>``` will print the following information on the log output:

```java
@RxLogMaybe
Maybe<Integer> number() {
  return Maybe.just(1);
}
```
```
Frodo2 => [@Maybe :: @InClass -> MaybeSamples :: @Method -> number()]
Frodo2 => [@Maybe#number -> onSubscribe()]
Frodo2 => [@Maybe#number -> onSuccess() -> 1]
Frodo2 => [@Maybe#number -> @Emitted -> 1 element :: @Time -> 0 ms]
Frodo2 => [@Maybe#number -> @ObserveOn -> RxNewThreadScheduler-1]
```

- **@RxLogCompletable:** Annotated functions which return ```io.reactivex.Completable``` will print the following information on the log output:

```java
@RxLogCompletable
Completable doSomething() {
  return Completable.fromAction(new Action() {
    @Override public void run() throws Exception {
      Thread.sleep(1000);
    }
  });
}
```
```
Frodo2 => [@Completable :: @InClass -> CompletableSamples :: @Method -> doSomething()]
Frodo2 => [@Completable#doSomething -> onSubscribe()]
Frodo2 => [@Completable#doSomething -> onComplete()]
Frodo2 => [@Completable#doSomething -> @Emitted -> 0 elements :: @Time -> 1003 ms]
```

Enabling Frodo 2
=========================

To enable Frodo, a gradle plugin must be applied in your ```build.gradle```:

```java
buildscript {
  repositories {
    jcenter()
  }
  dependencies {
    classpath "com.fernandocejas.frodo2:frodo2-plugin:$project.version"
  }
}

apply plugin: 'com.fernandocejas.frodo2'

//By default frodo2 is ON, although
//we can enable-disable it with this configuration.
frodo2 {
  enabled = true
}
```

Experimental
=========================

- Frodo 2 works only on **Android, Java and Kotlin*** projects using **Gradle** as Build System. 

- In **pure Java/Kotlin projects** all the debug information will be display using `System.out` (**NOT** the case of **Android** which displays on the **logcat**). The idea for future versions is to be able to use different Loggers like **Log4J** or any other alternative for example. 

- Contributions are more than welcome. 

Architecture Overview
=========================

It generates and weaves code based on annotations at compile time. This code is injected using [Aspect Oriented Programming](https://fernandocejas.com/2014/08/03/aspect-oriented-programming-in-android/) with [AspectJ](https://www.eclipse.org/aspectj/).

For more details please check these articles where there is a deeper explanation with implementation details:

- [Debugging RxJava on Android](https://fernandocejas.com/2015/11/05/debugging-rxjava-on-android/)

- [Aspect Oriented Programming in Android](https://fernandocejas.com/2014/08/03/aspect-oriented-programming-in-android/)

![aspectweaving](https://user-images.githubusercontent.com/1360604/40504693-4adf5a5e-5f92-11e8-89b3-e45c04b78745.png)


Known issues
=========================

1 - **Limitation**: Frodo 1 has the ability to detect ```@SubscribeOn``` thread but RxJava 2 does not offer any way to grab this information so only ```@ObserveOn``` thread information is displayed at the moment. 

2 - **On Android**: Multi module setup (application + android library) will not log annotated methods/classes from Android Library Module but will do it on Android Application Module. The reason behind this, is that the Android Gradle Plugin will build all Android Libraries as release versions, for instance, Frodo is not able to weave any code on the annotated methods/classes (Remember that only weaves in debug versions). There is a workaround for forcing debug versions of your Android Libraries (just be careful in case this is forgotten and you end up shipping a version of your app with RxJava Logging enabled) by adding this line in your ```build.gradle``` file:

```java
android {
  defaultPublishConfig "debug"
}
```

Local Development
=========================

Clone the repo and use the scripts listed below in order to to run/install/execute frodo 2 locally:

 * `./install_frodo2.sh` - One time execution command for installing frodo library and dependencies.
 * `./run_frodo2_android.sh` - Compiles frodo2 and run the android sample application.
 * `./run_frodo2_java` - Compiles frodo2 and run the java sample application.
 * `./gradlew runUnitTests` - Execute all unit tests in the project.
 
Contribution
=========================

Here you can download and install the java codestyle.
https://github.com/android10/java-code-styles 

License
=========================

    Copyright 2018 Fernando Cejas

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.


![http://www.fernandocejas.com](https://github.com/android10/Sample-Data/blob/master/android10/android10_logo_big.png)
