rootProject.name = "pz-extender"
include("extender-framework")

pluginManagement {
    repositories {
        mavenLocal()
        gradlePluginPortal()
        maven("https://www.jetbrains.com/intellij-repository/releases/")
    }
}
include("extender-api")
