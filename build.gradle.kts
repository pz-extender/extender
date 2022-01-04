import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("info.pzss.zomboid") version ("0.1.3")
    id("org.jetbrains.changelog") version ("1.3.1")
    kotlin("jvm") version ("1.6.10") apply (false)
    `maven-publish`
}

fun Project.properties(key: String) = findProperty(key)?.toString()

projectZomboid {
    zomboidPath.set(properties("zomboidPath"))
}

changelog {
    version.set(project.version as? String)
    groups.set(emptyList())
}

allprojects {
    group = "info.pzss.zomboid.extender"
    description = properties("description")
    version = properties("version")

    repositories {
        mavenCentral()
        pzLocal()
    }

    plugins.withType<JavaPlugin>() {
        configure<JavaPluginExtension> {
            sourceCompatibility = JavaVersion.VERSION_11
            targetCompatibility = JavaVersion.VERSION_11
        }
    }

    tasks.withType<KotlinCompile>().configureEach {
        kotlinOptions {
            jvmTarget = "11"
        }
    }

    tasks.withType<Test>().configureEach {
        useJUnitPlatform()
    }
}
