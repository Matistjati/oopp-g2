tasks.register("build") {
    group = "Build"
    description = "Build dashboard."

    doLast {
        project.exec {
            workingDir = project.projectDir
            commandLine("npm.cmd", "run", "build")
        }
    }
}