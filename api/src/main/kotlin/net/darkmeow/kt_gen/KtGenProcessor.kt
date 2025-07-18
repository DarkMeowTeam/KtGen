package net.darkmeow.kt_gen

import java.nio.file.Path

interface KtGenProcessor {
    fun process(inputs: List<Path>, outputDir: Path)
}