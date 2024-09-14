package moe.shinkiro.silicone.multiver

import moe.shinkiro.silicone.minecraft.SiliconeExtension
import org.gradle.api.Project
import java.nio.file.Paths

internal fun setupMultiver(project: Project) {

    val extMultiver = project.extensions.create("multiversion", MultiverExtension::class.java, project)

    val multiverFolder = Paths.get("multiver").also { it.toFile().mkdirs() }

    if (extMultiver.versions.isEmpty()) throw IllegalStateException("No versions registered in multiversion block.")

    extMultiver.versions.forEach { version ->
        val versionId = "${version.mcVersion}-${version.loader.name.lowercase()}"

        val versionFolder = multiverFolder.resolve(versionId).also { it.toFile().mkdirs() }

        val versionProject = project.subprojects.find { it.name == versionId } ?: throw IllegalStateException("ugh")

        versionProject.extensions.create("silicone", SiliconeExtension::class.java, project, version.mcVersion, version.loader)

        versionProject.afterEvaluate {

        }

    }

}