package me.azure.silicone.minecraft

import me.azure.silicone.mappings.resolveFabricMappings
import org.gradle.api.Project

public enum class ModLoader(
    internal val deobfTargetMappings: String,
    internal val resolveMappings: (Project, String) -> String
) {
    FABRIC(
        "yarn|named",
        ::resolveFabricMappings
    ),
    FORGE(
        "official|named", // mcp used as makeshift official on legacy versions
        { project, version ->
            TODO("No forge mapping file resolver implemented yet.")
        }
    )
}