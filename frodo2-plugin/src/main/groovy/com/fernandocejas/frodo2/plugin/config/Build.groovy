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

import org.gradle.api.Project

abstract class Build {

  protected static final String FRODO_VERSION = "0.9.0"
  protected static final String ASPECTJ_VERSION = "1.8.6"

  protected def project

  public Build(Project project) {
    this.project = project
    setupRepositories()
    setupDependencies()
  }

  private void setupRepositories() {
    this.project.buildscript.repositories.maven {
      url "http://dl.bintray.com/android10/maven"
    }

    this.project.repositories.maven {
      url "http://dl.bintray.com/android10/maven"
    }
  }

  private void setupDependencies() {
    project.dependencies {
      compile "com.fernandocejas.frodo2:frodo2-api:$FRODO_VERSION"
    }
  }

  abstract void configure()
}
