

Frodo 2 - WIP - Coming soon! [![Build Status](https://travis-ci.org/android10/frodo2.svg?branch=master)](https://travis-ci.org/android10/frodo2)
=========================

[![Hex.pm](https://img.shields.io/hexpm/l/plug.svg)](http://www.apache.org/licenses/LICENSE-2.0) 
[![Platform](https://img.shields.io/badge/platform-kotlin-blue.svg)](https://kotlinlang.org/)
[![Platform](https://img.shields.io/badge/platform-android-green.svg)](http://developer.android.com/index.html)
[![Platform](https://img.shields.io/badge/platform-java-orange.svg)](https://docs.oracle.com/javase/8/docs/)

```Frodo 2``` is the second generation version of [Frodo](https://github.com/android10/frodo/), mainly used for making easier debugging [RxJava 2](https://github.com/ReactiveX/RxJava/wiki/What's-different-in-2.0). 
It generates and weaves code based on annotations only on ```debug``` versions of the application where the plugin is applied, for instance, it is safe to persist any ```Frodo 2``` annotation in the codebase.

![frodo_hug](https://cloud.githubusercontent.com/assets/1360604/10925718/e7ea4318-8290-11e5-91b4-f2bfbde65319.gif)


Main Features
=========================

- **@RxLogObservable:** Annotated methods which return ```io.reactivex.Observables``` will print the following information when emitting items:

<img width="767" alt="frodo_observable" src="https://cloud.githubusercontent.com/assets/1360604/10925000/ee937c08-828a-11e5-97ac-bb13b7d469f8.png">

```java
    @RxLogObservable
    public Observable<List<MyDummyClass>> list() {
        return Observable.just(buildDummyList());
    }
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

Known issues
=========================

1 - Multi module setup (application + android library) will not log annotated methods/classes from Android Library Module but will do it on Android Application Module. The reason behind this, is that the Android Gradle Plugin will build all Android Libraries as release versions, for instance, Frodo is not able to weave any code on the annotated methods/classes (Remember that only weaves in debug versions). There is a workaround for forcing debug versions of your Android Libraries (just be careful in case this is forgotten and you end up shipping a version of your app with RxJava Logging enabled) by adding this line in your ```build.gradle``` file:

```java
android {
  defaultPublishConfig "debug"
}
```

Architecture
=========================

WIP

Local Development
=========================

Clone the repo and use the scripts listed below in order to to run/install/execute frodo 2 locally:

 * `./gradlew clean build` - Build the entire example and execute unit and integration tests plus lint check.
 * `./gradlew installDebug` - Install the debug apk on the current connected device.
 * `./gradlew installDebug` - Install the debug apk on the current connected device.
 * `./gradlew runUnitTests` - Execute all unit tests within the project.
 
Contribution
=========================

Here you can download and install the java codestyle.
https://github.com/android10/java-code-styles 

License
=========================

    Copyright 2017 Fernando Cejas

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
