import info.pzss.zomboid.gradle.ProjectZomboidLaunchTask
import info.pzss.zomboid.gradle.ProjectZomboidLaunchTask.*
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.jetbrains.gradle.plugin.idea-ext") version ("1.1.1")
    id("info.pzss.zomboid") version ("0.1.2")
    kotlin("jvm") version ("1.6.10") apply (false)
}

val zomboidPath : String by project
val projectVersion : String by project

projectZomboid {
    gamePath.set(zomboidPath)
}

allprojects {
    group = "info.pzss.zomboid.extender"
    version = projectVersion

    repositories {
        mavenCentral()
        pzLocal()
    }

    plugins.withType<JavaPlugin>() {
        configure<JavaPluginExtension> {
            sourceCompatibility = JavaVersion.VERSION_15
            targetCompatibility = JavaVersion.VERSION_15
        }
    }

    tasks.withType<KotlinCompile>().configureEach {
        kotlinOptions {
            jvmTarget = "15"
        }
    }

    tasks.withType<Test>().configureEach {
        useJUnitPlatform()
    }
}
