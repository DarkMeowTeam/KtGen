package net.darkmeow.kt_gen

import org.gradle.api.file.DirectoryProperty
import org.gradle.api.file.FileCollection
import org.gradle.api.provider.Property
import org.gradle.api.tasks.InputFiles
import org.gradle.api.tasks.JavaExec
import org.gradle.api.tasks.OutputDirectory

abstract class KtGenTask : JavaExec() {
    @get:InputFiles
    abstract val inputFiles: Property<FileCollection>

    @get:OutputDirectory
    abstract val outputDir: DirectoryProperty

    @get:InputFiles
    abstract val runtimeClasspath: Property<FileCollection>

    init {
        mainClass.set("net.darkmeow.kt_gen.KtGenRuntime")
    }

    override fun exec() {
        classpath = runtimeClasspath.get()
        environment("INPUT_PATHS", inputFiles.get().asPath)
        args = listOf(outputDir.asFile.get().absolutePath)

        super.exec()
    }
}