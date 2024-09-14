package moe.shinkiro.silicone.multiver

import moe.shinkiro.silicone.minecraft.ModLoader

internal data class VersionDefinition(
    val mcVersion: String,
    val loader: ModLoader
)
