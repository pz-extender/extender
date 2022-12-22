import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import info.pzss.zomboid.gradle.ProjectZomboidLaunchTask

plugins {
    kotlin("jvm")
    `maven-publish`
    signing
    id("com.github.johnrengelman.shadow")
}

val aspectj by configurations.creating

dependencies {
    api(project(":extender-api"))

    compileOnly(pzGameApi())
    compileOnly(pzGameLibs())

    implementation(kotlin("scripting-jvm-host"))
    implementation(kotlin("scripting-jvm"))
    implementation(kotlin("stdlib"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.0")

    api("org.aspectj:aspectjrt:1.9.7")
    aspectj("org.aspectj:aspectjweaver:1.9.19")

    implementation("io.github.classgraph:classgraph:4.8.138")

    implementation("org.slf4j:slf4j-api:1.7.32")
    implementation("ch.qos.logback:logback-classic:1.2.10")
    implementation("ch.qos.logback:logback-core:1.2.10")
    implementation("io.github.microutils:kotlin-logging-jvm:2.1.21")

    implementation("com.google.guava:guava:31.0.1-jre")

    testImplementation("org.junit.jupiter:junit-jupiter:5.7.0")
}

publishing {
    publications.create<MavenPublication>("maven") {
        from(components["java"])

        pom {
            packaging = "jar"
            name.set("Project Zomboid Extender Framework")
            description.set(
                """
                Extension framework and patcher to aid in extending Project Zomboid with new functionality.
            """.trimIndent()
            )
        }
    }
}

signing {
    sign(publishing.publications["maven"])
}

tasks.create<ProjectZomboidLaunchTask>("pzLaunch64") {
    additionalJvmArgs.set(
        listOf(
            "-javaagent:${aspectj.asPath}",
            "--add-exports=java.base/sun.nio.ch=ALL-UNNAMED",
            "--add-opens=java.base/java.lang=ALL-UNNAMED",
            "--add-opens=java.base/java.lang.reflect=ALL-UNNAMED",
            "--add-opens=java.base/java.io=ALL-UNNAMED",
            "--add-exports=jdk.unsupported/sun.misc=ALL-UNNAMED"
        )
    )

    launchSettings.set("ProjectZomboid64.json")
    classpath = configurations.runtimeClasspath.get() + sourceSets.main.get().runtimeClasspath + pzGameRuntime()

    dependsOn(tasks.classes)
}

val shadowJar = tasks.getByName<ShadowJar>("shadowJar")

tasks.create<Zip>("distZip") {
    archiveFileName.set("pz-extender-${project.version}.zip")
    destinationDirectory.set(layout.buildDirectory.dir("dist"))

    into("libs") {
        from(aspectj)
    }

    from(layout.projectDirectory.dir("src/main/distribution").file("README"))
    from(shadowJar.outputs)
}
