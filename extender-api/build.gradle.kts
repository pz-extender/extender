plugins {
    kotlin("jvm")
}

repositories {
    pzLocal()
}

dependencies {
    compileOnly(pzGameApi())

    implementation(kotlin("stdlib"))
    implementation(kotlin("scripting-jvm"))

    implementation("com.google.guava:guava:31.0.1-jre")
}