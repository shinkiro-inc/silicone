package me.azure.silicone

import me.azure.silicone.dependencies.setupShading
import me.azure.silicone.multiver.MultiverExtension
import me.azure.silicone.multiver.setupMultiver
import org.gradle.api.Plugin
import org.gradle.api.Project
import java.nio.file.Paths

public typealias Silicone = SiliconePlugin

public class SiliconePlugin : Plugin<Project> {

    override fun apply(target: Project) {

        setupShading(target)

        SiliconeCache.initialize(target)

        setupMultiver(target)

    }

}