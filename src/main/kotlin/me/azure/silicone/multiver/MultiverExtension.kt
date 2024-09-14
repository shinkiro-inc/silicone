package me.azure.silicone.multiver

import me.azure.silicone.minecraft.ModLoader
import org.gradle.api.Project
import org.gradle.api.provider.Property
import javax.inject.Inject

public open class MultiverExtension
@Inject constructor(project: Project) {
    private val objects = project.objects

    internal val versions = mutableListOf<VersionDefinition>()

    public val version: Property<String> = objects.property(String::class.java)
    public val loader: Property<ModLoader> = objects.property(ModLoader::class.java)

    public fun version(version: String, loader: ModLoader) {
        val def = VersionDefinition(version, loader)

        if (versions.contains(def)) throw IllegalArgumentException("Double registration of environment $version-${loader.name.lowercase()}")

        versions.add(def)
    }

}