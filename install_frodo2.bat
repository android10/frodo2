gradlew -p frodo2-api/ assemble install --configure-on-demand
gradlew -p frodo2-runtime-android/ assembleDebug install --configure-on-demand -x lint
./gradlew -p frodo2-runtime-java/ clean build install --configure-on-demand
gradlew -p  frodo2-plugin/ build install --configure-on-demand
