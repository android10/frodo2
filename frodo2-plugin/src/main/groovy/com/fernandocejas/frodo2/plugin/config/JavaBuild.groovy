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

import com.fernandocejas.frodo2.plugin.aspect.AspectCompiler
import org.gradle.api.Project
import org.gradle.api.tasks.compile.JavaCompile

class JavaBuild extends Build {

  JavaBuild(Project project) {
    super(project)
  }

  @Override
  void configure() {
    project.dependencies {
      implementation "org.aspectj:aspectjrt:$ASPECTJ_VERSION"
      implementation "com.fernandocejas.frodo2:frodo2-runtime-java:$FRODO_VERSION"
      implementation "com.fernandocejas.frodo2:frodo2-logger:$FRODO_VERSION"
    }

    final JavaCompile javaCompile = project.compileJava
    javaCompile.doLast {
      if (project.frodo2.enabled) {
        final String[] args = ["-showWeaveInfo",
                               "-1.5",
                               "-XnoInline",
                               "-Xlint:warning",
                               "-inpath", javaCompile.destinationDir.toString(),
                               "-inpath", javaCompile.inputs.files.asPath.toString(),
                               "-aspectpath", javaCompile.classpath.asPath,
                               "-d", javaCompile.destinationDir.toString(),
                               "-classpath", javaCompile.classpath.asPath]
        new AspectCompiler(logger).compile(args)
      }
    }
  }
}
