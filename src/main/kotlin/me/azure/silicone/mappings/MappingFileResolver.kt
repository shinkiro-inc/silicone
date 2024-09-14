package me.azure.silicone.mappings

import org.gradle.api.Project

internal fun resolveFabricMappings(project: Project, version: String): String {
    return "net.fabricmc:yarn:$version"
}

