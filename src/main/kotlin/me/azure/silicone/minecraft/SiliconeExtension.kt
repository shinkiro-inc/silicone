package me.azure.silicone.minecraft

import me.azure.silicone.multiver.VersionDefinition
import org.gradle.api.Project
import org.gradle.api.provider.Property
import javax.inject.Inject

public open class SiliconeExtension
@Inject constructor(
    project: Project,
    public val version: String,
    public val loader: ModLoader
) {
    private val objects = project.objects



}