import org.jetbrains.gradle.ext.settings
import org.jetbrains.gradle.ext.taskTriggers
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("info.pzss.zomboid") version ("0.2.0")
    id("org.jetbrains.changelog") version ("1.3.1")
    id("com.github.johnrengelman.shadow") version ("7.1.2")
    id("org.jetbrains.gradle.plugin.idea-ext") version("1.1.8")
    kotlin("jvm") version ("2.0.20") apply (false)
    `maven-publish`
    idea
}

fun Project.properties(key: String) = findProperty(key)?.toString() ?: error("Unable to find property $key")

projectZomboid {
    zomboidPath.set(properties("zomboidPath"))
}

changelog {
    version.set(project.version as? String)
    groups.set(emptyList())
}

idea {
    module {
        isDownloadJavadoc = true
        isDownloadSources = true
    }

    project {
        settings {
            taskTriggers {
                resolveTasks(tasks.getByPath(":projectZomboidJar"))
            }
        }
    }
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

allprojects {
    group = properties("group")
    description = properties("description")
    version = properties("version")

    repositories {
        mavenCentral()
        mavenLocal()
    }

    plugins.withType<MavenPublishPlugin> {
        configure<PublishingExtension> {
            repositories {
                maven {
                    val releasesRepoUrl = "https://s01.oss.sonatype.org/content/repositories/releases/"
                    val snapshotsRepoUrl = "https://s01.oss.sonatype.org/content/repositories/snapshots/"

                    url = uri(if (version.toString().endsWith("SNAPSHOT")) snapshotsRepoUrl else releasesRepoUrl)
                    credentials(PasswordCredentials::class)
                }
            }

            publications.withType<MavenPublication> {
                artifactId = project.name

                pom {
                    url.set("https://github.com/pz-extender/")
                    inceptionYear.set("2022")

                    organization {
                        name.set("Project Zomboid Extender Authors")
                        url.set("https://github.com/pz-extender/")
                    }

                    licenses {
                        license {
                            name.set("Apache Software License 2.0")
                            url.set("https://opensource.org/licenses/Apache-2.0")
                        }

                        license {
                            name.set("MIT License")
                            url.set("https://opensource.org/licenses/MIT")
                        }
                    }

                    scm {
                        connection.set("scm:git:https://github.com/pz-extender/extender.git")
                        developerConnection.set("scm:git:git@github.com:pz-extender/extender.git")
                        url.set("https://github.com/pz-extender/extender.git")
                    }

                    issueManagement {
                        system.set("GitHub")
                        url.set("https://github.com/pz-extender/extender/issues")
                    }

                    ciManagement {
                        system.set("GitHub Actions")
                        url.set("https://github.com/pz-extender/extender/actions")
                    }
                }
            }
        }
    }

    plugins.withType<SigningPlugin> {
        configure<SigningExtension> {
            useGpgCmd()
        }
    }

    plugins.withType<JavaPlugin>() {
        configure<JavaPluginExtension> {
            withSourcesJar()
            withJavadocJar()

            sourceCompatibility = JavaVersion.VERSION_21
            targetCompatibility = JavaVersion.VERSION_21
        }
    }

    tasks.withType<KotlinCompile>().configureEach {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_21)
        }
    }

    tasks.withType<Test>().configureEach {
        useJUnitPlatform()
    }
}
