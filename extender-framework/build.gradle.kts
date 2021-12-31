import info.pzss.zomboid.gradle.ProjectZomboidLaunchTask

plugins {
    kotlin("jvm")
}

val aspectj by configurations.creating

dependencies {
    compileOnly(pzGameApi())
    aspectj("org.aspectj:aspectjweaver:1.9.7")
    implementation("org.aspectj:aspectjrt:1.9.7")
    implementation("com.google.guava:guava:31.0.1-jre")
    implementation("org.slf4j:slf4j-api:1.7.32")
    implementation("ch.qos.logback:logback-classic:1.2.10")
    implementation("ch.qos.logback:logback-core:1.2.10")
    implementation("io.github.microutils:kotlin-logging-jvm:2.1.21")
    runtimeOnly("org.fusesource.jansi:jansi:1.18")
}


tasks.create<ProjectZomboidLaunchTask>("pzLaunch64") {
    launchType.set(ProjectZomboidLaunchTask.LaunchType.CLIENT)
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
    classpath = configurations.runtimeClasspath.get() + sourceSets.main.get().runtimeClasspath

    dependsOn(tasks.classes)
}
