rootProject.name = "pz-extender"

pluginManagement {
    repositories {
        mavenLocal()
        gradlePluginPortal()
        maven("https://www.jetbrains.com/intellij-repository/releases/")
    }
}

include("extender-api")
include("extender-framework")
