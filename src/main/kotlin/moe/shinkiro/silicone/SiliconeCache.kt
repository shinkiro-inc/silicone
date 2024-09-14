package moe.shinkiro.silicone

import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import moe.shinkiro.silicone.minecraft.*
import org.gradle.api.Project
import java.io.File
import java.nio.file.Path
import kotlin.collections.MutableMap
import kotlin.collections.component1
import kotlin.collections.component2
import kotlin.collections.find
import kotlin.collections.forEach
import kotlin.collections.mutableMapOf
import kotlin.collections.set
import kotlin.collections.toMap
import kotlin.io.path.*

@Serializable
private data class CacheManifest(
    val lastResolve: MutableMap<String, Long>
)

private fun Path.readOrNull(): String? {
    return if (this.exists()) this.readText() else null
}

private inline fun <reified T : Any> String?.deserialize(): T? {
    if (this == null) return null

    return json.decodeFromString(this)
}

private const val MANIFEST_EXPIRY = 1000 * 60 * 60 * 24 // 1 day

public object SiliconeCache {

    private lateinit var homePath: Path
    private lateinit var versionsPath: Path

    private lateinit var manifest: CacheManifest

    private fun updateManifest(file: String) {
        manifest.lastResolve[file] = System.currentTimeMillis()
    }

    private inline fun <reified T : Any> readFromManifest(path: Path, file: String): T? {
        if ((manifest.lastResolve[file] ?: 0) < System.currentTimeMillis() + MANIFEST_EXPIRY) {
            return json.decodeFromString<T>(path.resolve(file).readOrNull() ?: return null)
        }

        return null
    }

    @Suppress("SetterBackingFieldAssignment")
    internal var cacheVersionManifest: VersionManifest? = null
        get() {
            if (field == null) {
                field = readFromManifest(versionsPath, "version_manifest_v2.json") ?: resolveManifest().also { updateManifest("version_manifest_v2.json") }
                versionsPath.resolve("version_manifest_v2.json").writeText(json.encodeToString<VersionManifest>(field!!))
            }

            return field
        }
        // get NO-OPed lmao
        set(_) {}

    internal fun getVersionData(version: String): VersionData {
        return readFromManifest<VersionData>(versionsPath, "version_manifest.${version}.json")
            ?: (cacheVersionManifest!!.versions.find { it.id == version }?.run {
                val download = get(this.url) ?: throw RuntimeException("Failed to download '$version' version manifest, please investigate.")

                versionsPath.resolve("version_manifest.${version}.json".also { updateManifest(it) }).writeText(download)

                json.decodeFromString(download)
            } ?: throw IllegalArgumentException("What the fuck? Invalid version when trying to look up '$version', please investigate."))
    }

    @OptIn(ExperimentalPathApi::class)
    internal fun initialize(project: Project) {
        homePath = project.gradle.gradleUserHomeDir.resolve(".silicone").also(File::mkdirs).toPath()
        versionsPath = homePath.resolve("versions").toFile().also(File::mkdirs).toPath()

        manifest = homePath.resolve("cache_manifest.json").readOrNull().deserialize<CacheManifest>() ?: CacheManifest(mutableMapOf())

        project.tasks.register("nukeCache") { it ->
            it.group = "silicone"
            it.description = "Deletes the entire Silicone cache."

            it.doLast {
                homePath.forEach(Path::deleteRecursively)
            }
        }

        project.tasks.register("invalidateCache") {
            it.group = "silicone"
            it.description = "Invalidates Mojang API results cached by Silicone."

            it.doLast {
                homePath.resolve("cache_manifest.json").deleteIfExists()
            }
        }

        project.tasks.register("forceRevalidateCache") {
            it.group = "silicone"
            it.description = "Forces the current caches to be considered valid for 24h. Useful if you don't have internet."

            it.doLast {
                val rn = System.currentTimeMillis()
                manifest.lastResolve.toMap().forEach { (file, _) ->
                    manifest.lastResolve[file] = rn
                }
            }
        }

        project.afterEvaluate {
            homePath.resolve("cache_manifest.json").writeText(json.encodeToString(manifest))
        }

    }

}