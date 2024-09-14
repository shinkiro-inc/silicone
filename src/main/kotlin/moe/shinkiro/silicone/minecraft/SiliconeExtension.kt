package moe.shinkiro.silicone.minecraft

import org.gradle.api.Project
import javax.inject.Inject

public open class SiliconeExtension
@Inject constructor(
    project: Project,
    public val version: String,
    public val loader: ModLoader
) {
    private val objects = project.objects



}