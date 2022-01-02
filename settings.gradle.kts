rootProject.name = "pz-extender"
include("extender-framework")

pluginManagement {
    repositories {
        mavenLocal()
        gradlePluginPortal()
        maven("https://repo.openrs2.org/repository/openrs2/")
    }
}
include("extender-api")
