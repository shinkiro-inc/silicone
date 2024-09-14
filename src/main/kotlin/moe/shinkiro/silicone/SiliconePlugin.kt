package moe.shinkiro.silicone

import moe.shinkiro.silicone.dependencies.setupShading
import moe.shinkiro.silicone.multiver.setupMultiver
import org.gradle.api.Plugin
import org.gradle.api.Project

public typealias Silicone = SiliconePlugin

public class SiliconePlugin : Plugin<Project> {

    override fun apply(target: Project) {

        setupShading(target)

        SiliconeCache.initialize(target)

        setupMultiver(target)

    }

}