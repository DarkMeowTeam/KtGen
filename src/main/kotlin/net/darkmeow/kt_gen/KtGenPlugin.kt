package net.darkmeow.kt_gen

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.SourceSetContainer

class KtGenPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        val sourceSetContainer = project.extensions.getByType(SourceSetContainer::class.java)
        val srcDir = project.layout.buildDirectory.dir("generated/sources/ktgen/main/kotlin")

        val ktgenInput = project.configurations.create("ktgenInput")
        val ktgenImpl = project.configurations.create("ktgen")

        project.dependencies.add("ktgen", "net.darkmeow:ktgen-api:$version")
        project.dependencies.add("ktgen", "net.darkmeow:ktgen-runtime:$version")

        sourceSetContainer.named("main").configure { main ->
            main.java.srcDir(srcDir)
            project.tasks.named(main.getCompileTaskName("kotlin")).configure {
                it.dependsOn("ktgen")
            }
        }

        val ktgenTask = project.tasks.register("ktgen", KtGenTask::class.java) { task ->
            task.inputFiles.set(project.files(ktgenInput.elements.map { set ->
                set.map { it.asFile }.filter { it.exists() }.map {
                    if (it.isDirectory) {
                        it
                    } else {
                        when (it.extension) {
                            "jar", "zip" -> project.zipTree(it)
                            else -> it
                        }
                    }
                }
            }))
            task.runtimeClasspath.set(ktgenImpl)
            task.outputDir.set(srcDir)
        }

        project.tasks.findByName("sourcesJar")?.dependsOn(ktgenTask)
    }

    companion object {
        val version = KtGenPlugin::class.java.getResource("/ktgen-plugin.version")!!.readText()
    }
}