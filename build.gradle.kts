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
    group = properties("group")
    description = properties("description")
    version = properties("version")

    repositories {
        mavenCentral()
        pzLocal()
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
