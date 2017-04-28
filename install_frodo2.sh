#!/bin/bash
./gradlew -p frodo2-api/ clean assemble install --configure-on-demand
./gradlew -p frodo2-logger/ clean assemble install --configure-on-demand
./gradlew -p frodo2-runtime-android/ clean assembleDebug install --configure-on-demand -x lint
./gradlew -p frodo2-runtime-java/ clean build install --configure-on-demand
./gradlew -p  frodo2-plugin/ clean build install --configure-on-demand
