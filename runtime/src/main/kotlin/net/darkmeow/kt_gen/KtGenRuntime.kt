package net.darkmeow.kt_gen

import org.jetbrains.kotlin.konan.file.File
import java.nio.file.Paths
import java.util.ServiceLoader

object KtGenRuntime {
    @JvmStatic
    fun main(args: Array<String>) {
        val inputs = System.getenv("INPUT_PATHS").split(File.Companion.pathSeparator).map { Paths.get(it) }
        val outputDir = Paths.get(args[0])
        with(outputDir.toFile()) {
            deleteRecursively()
            mkdirs()
        }
        val services = ServiceLoader.load(KtGenProcessor::class.java)
        for (service in services) {
            service.process(inputs, outputDir)
        }
    }
}