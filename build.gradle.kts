import info.pzss.zomboid.gradle.ProjectZomboidLaunchTask
import info.pzss.zomboid.gradle.ProjectZomboidLaunchTask.*

plugins {
    id("org.jetbrains.gradle.plugin.idea-ext") version ("1.1.1")
    id("info.pzss.zomboid.gradle") version ("0.1.1")
    kotlin("jvm") version ("1.6.10") apply (false)
}

val zomboidPath : String by project

projectZomboid {
    gamePath.set(zomboidPath)
}

allprojects {
    group = "com.pzss.extender"
    version = "0.1.0-SNAPSHOT"

    repositories {
        mavenCentral()
        pzLocal()
    }

    tasks.withType<Test>().configureEach {
        useJUnitPlatform()
    }
}
