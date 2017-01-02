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
package com.fernandocejas.frodo2.plugin.config

import org.aspectj.bridge.IMessage
import org.aspectj.bridge.MessageHandler
import org.aspectj.tools.ajc.Main
import org.gradle.api.Project
import org.gradle.api.tasks.compile.JavaCompile

class JavaBuild extends Build {

  JavaBuild(Project project) {
    super(project)
  }

  @Override
  void configure() {

    project.dependencies {
      compile "org.aspectj:aspectjrt:1.8.6"
      compile "com.fernandocejas.frodo2:frodo2-runtime-java:0.9.0"
    }


    final def log = project.logger
    final JavaCompile javaCompile = project.compileJava

    System.println "\n" + " -inpath --->> " + javaCompile.destinationDir.toString()
    System.println "\n" + " -inpath --->> " + javaCompile.dependencyCacheDir.toString()
    System.println "\n" + " -inpath --->> " + javaCompile.path.toString()
    System.println "\n" + " -aspectpath --->> " + javaCompile.classpath.asPath
    System.println("\n" + " -d --->> " + javaCompile.destinationDir.toString())
    System.println "\n" + " -classpath --->> " + javaCompile.classpath.asPath
    System.println "\n" + " -bootclasspath --->> " + System.getProperty("sun.boot.class.path")
    System.println "\n" + " -compileArgs --->> " + javaCompile.options.compilerArgs.toString()
    System.println "\n"

    javaCompile.doLast {
      String[] args = ["-showWeaveInfo",
                       "-1.5",
                       "-XnoInline",
                       "-Xlint:warning",
                       "-inpath", javaCompile.destinationDir.toString(),
                       "-aspectpath", javaCompile.classpath.asPath,
                       "-d", javaCompile.destinationDir.toString(),
                       "-classpath", javaCompile.classpath.asPath]

      final MessageHandler handler = new MessageHandler(true);
      new Main().run(args, handler);
      for (IMessage message : handler.getMessages(null, true)) {
        switch (message.getKind()) {
          case IMessage.ABORT:
          case IMessage.ERROR:
          case IMessage.FAIL:
            log.error message.message, message.thrown
            break;
          case IMessage.WARNING:
            log.warn message.message, message.thrown
            break;
          case IMessage.INFO:
            log.info message.message, message.thrown
            break;
          case IMessage.DEBUG:
            log.debug message.message, message.thrown
            break;
        }
      }
    }
  }
}
