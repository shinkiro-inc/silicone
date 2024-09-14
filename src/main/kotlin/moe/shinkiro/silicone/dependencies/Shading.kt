package moe.shinkiro.silicone.dependencies

import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import org.gradle.api.NamedDomainObjectProvider
import org.gradle.api.Project
import org.gradle.api.artifacts.Configuration
import org.gradle.api.file.DuplicatesStrategy
import org.gradle.api.plugins.JavaPluginExtension

public lateinit var shaded: NamedDomainObjectProvider<Configuration>

public open class ShadedJar : ShadowJar() {

    init {
        this.duplicatesStrategy = DuplicatesStrategy.WARN
        this.configurations = listOf(shaded.get())

        val javaPlugin = project.extensions.getByType(JavaPluginExtension::class.java)
        val jarTask = project.tasks.getByName("jar")

        manifest.inheritFrom(jarTask)

        @Suppress("LeakingThis")
        from(javaPlugin.sourceSets.getByName("main").output)
    }

}

internal fun setupShading(project: Project) {

    val shadedJars = project.configurations.register("shaded") {}

    shaded = shadedJars

    val shadedJar = project.tasks.register("shadedJar", ShadedJar::class.java) {
        it.group = "build"
        it.description = "Builds a JAR and shades dependencies marked as shaded."

        it.archiveAppendix.set("Shaded")
    }

    project.artifacts.add("shaded", shadedJar)

}