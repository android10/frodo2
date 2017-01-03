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

import org.gradle.api.Project
import org.gradle.api.tasks.TaskInstantiationException

class BuildFactory {

  private static final String ANDROID_APP = 'com.android.application'
  private static final String ANDROID_LIBRARY = 'com.android.library'
  private static final String JAVA_APP = 'application'
  private static final String JAVA_LIBRARY = 'java'

  private BuildFactory() {}

  static def create(Project project) {
    if (project.pluginManager.hasPlugin(ANDROID_APP) ||
        project.pluginManager.hasPlugin(ANDROID_LIBRARY)) {
      new AndroidBuild(project)

    } else if (project.pluginManager.hasPlugin(JAVA_APP) ||
        project.pluginManager.hasPlugin(JAVA_LIBRARY)) {
      new JavaBuild(project)

    } else {
      throw new TaskInstantiationException('Android or Java plugins required')
    }
  }
}
