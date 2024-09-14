package me.azure.silicone.multiver

import me.azure.silicone.minecraft.ModLoader

internal data class VersionDefinition(
    val mcVersion: String,
    val loader: ModLoader
)
