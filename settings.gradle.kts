rootProject.name = "pz-extender"

pluginManagement {
    repositories {
        mavenLocal()
        gradlePluginPortal()
        maven("https://www.jetbrains.com/intellij-repository/releases/")
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}

include("extender-api")
include("extender-framework")
