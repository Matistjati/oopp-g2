plugins {
    application
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":lib"))
    implementation("io.vertx:vertx-core:4.5.0")
    implementation("io.vertx:vertx-web:4.5.0")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.16.0")
}

application {
    mainClass.set("landrive.webserver.app.App")
}

tasks.register<Copy>("copyWebFiles") {
    from(project(":dashboard").file("dist"))
    into(layout.buildDirectory.dir("resources/main/webfiles"))
}

project.tasks.findByName("jar")?.dependsOn(":dashboard:build", "copyWebFiles")