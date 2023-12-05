plugins {
    `java-library`
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.vertx:vertx-core:4.5.0")
    implementation("io.vertx:vertx-web:4.5.0")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.16.0")
}