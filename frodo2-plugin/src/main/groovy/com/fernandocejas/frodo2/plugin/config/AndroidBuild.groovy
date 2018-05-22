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
 * limitations under the License.*/
package com.fernandocejas.frodo2.plugin.config

import com.android.build.gradle.AppPlugin
import com.fernandocejas.frodo2.plugin.aspect.AspectCompiler
import org.gradle.api.Project
import org.gradle.api.tasks.compile.JavaCompile

class AndroidBuild extends Build {

  AndroidBuild(Project project) {
    super(project)
  }

  @Override
  void configure() {
    project.dependencies {
      debugImplementation "org.aspectj:aspectjrt:$ASPECTJ_VERSION"
      debugImplementation "com.fernandocejas.frodo2:frodo2-runtime-android:$FRODO_VERSION"
      debugImplementation "com.fernandocejas.frodo2:frodo2-logger:$FRODO_VERSION"
    }

    final def log = project.logger
    final def variants = getProjectVariants project
    variants.all { variant ->
      if (!variant.buildType.isDebuggable()) {
        log.debug("Skipping non-debuggable build type '${variant.buildType.name}'.")
        return
      } else if (!project.frodo2.enabled) {
        log.debug('Frodo2 is not enabled.')
        return
      }

      final JavaCompile javaCompile = variant.javaCompile
      javaCompile.doLast {
        final String[] args = ["-showWeaveInfo",
                               "-1.5",
                               "-inpath", javaCompile.destinationDir.toString(),
                               "-aspectpath", javaCompile.classpath.asPath,
                               "-d", javaCompile.destinationDir.toString(),
                               "-classpath", javaCompile.classpath.asPath,
                               "-bootclasspath", project.android.bootClasspath.join(File.pathSeparator)]
        new AspectCompiler(logger).compile(args)
      }
    }
  }

  static def getProjectVariants(Project project) {
    if (project.plugins.hasPlugin(AppPlugin)) {
      project.android.applicationVariants
    } else {
      project.android.libraryVariants
    }
  }
}
