#!/bin/bash
./install_frodo2.sh
./gradlew clean -p frodo2-sample-android assembleDebug installDebug
adb shell am start -n com.fernandocejas.example.frodo2.debug/com.fernandocejas.example.frodo2.MainActivity
