rootProject.name = "burger-std"

buildscript {
  repositories {
    maven("https://pommes.burgerbude.org/api/v1/maven/production/libraries/")
  }

  dependencies {
    classpath("org.burgerbude.gradle", "burger-plugin", "0.1.2")
  }
}

apply(plugin = "org.burgerbude.burger")

burgerSettings {
  isRootProjectPrefix = true

  defineModule("file-watcher")
}