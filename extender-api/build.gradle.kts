plugins {
    kotlin("jvm")
    `maven-publish`
    `java-library`
}

repositories {
    pzLocal()
}

publishing {
    publications {
        register<MavenPublication>("extenderApi") {
            from(components["java"])
        }
    }
}

dependencies {
    compileOnly(pzGameApi())
    compileOnly(pzGameLibs())

    api(kotlin("script-runtime"))
    implementation(kotlin("stdlib"))
    implementation(kotlin("scripting-jvm"))

    implementation("com.google.guava:guava:31.0.1-jre")
}