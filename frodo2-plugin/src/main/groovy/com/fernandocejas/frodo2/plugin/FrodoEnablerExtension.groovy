package com.fernandocejas.frodo2.plugin

class FrodoEnablerExtension {
  def enabled = true

  def setEnabled(boolean enabled) {
    this.enabled = enabled
  }

  def getEnabled() {
    return enabled;
  }
}
