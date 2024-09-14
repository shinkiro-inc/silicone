package moe.shinkiro.silicone.minecraft

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import moe.shinkiro.silicone.multiver.VersionDefinition
import org.gradle.api.Project
import java.net.URL

internal const val META_MANIFEST = "https://launchermeta.mojang.com/mc/game/version_manifest_v2.json"

@OptIn(ExperimentalSerializationApi::class)
internal val json = Json {
    this.ignoreUnknownKeys = true
    this.encodeDefaults = false
    this.explicitNulls = false
    this.decodeEnumsCaseInsensitive = true
}

internal enum class MojangOS {
    WINDOWS,
    LINUX,
    OSX
}

internal enum class MojangRule {
    ALLOW,
    DISALLOW
}

@Serializable
internal data class VersionManifestEntry(
    val url: String,
    val id: String
)

@Serializable
internal data class VersionManifest(
    val versions: List<VersionManifestEntry>
)

@Serializable
internal data class VersionDataDownloadEntry(
    val sha1: String,
    val size: Long,
    val url: String
)

@Serializable
internal data class VersionDataLoggingConfigEntry(
    val argument: String,
    val file: VersionDataDownloadEntry,
    val type: String
)

@Serializable // this is really getting on my nerves
internal data class VersionDataLibraryOS(
    val name: MojangRule
)

@Serializable
internal data class VersionDataLibraryRules(
    val action: MojangRule,
    val os: VersionDataLibraryOS? = null
)

@Serializable
internal data class VersionDataLibraryDownloads(
    val artifact: VersionDataDownloadEntry,
    val classifiers: Map<String, VersionDataDownloadEntry>? = null
)

@Serializable
internal data class VersionDataLibraryEntry(
    val name: String,
    val rules: List<VersionDataLibraryRules>? = null,
    val natives: Map<MojangOS, String>? = null,
    val downloads: VersionDataLibraryDownloads
)

@Serializable
internal data class VersionData(
    val assets: String,
    val id: String,
    val mainClass: String,
    val downloads: Map<String, VersionDataDownloadEntry>,
    val minecraftArguments: String,
    val logging: Map<String, VersionDataLoggingConfigEntry>,
    val assetIndex: VersionDataDownloadEntry,
    val libraries: List<VersionDataLibraryEntry>
)

internal fun get(url: String): String? = runCatching {
    URL(url).openStream().readBytes().decodeToString()
}.onFailure {
    it.printStackTrace()
}.getOrNull()

internal fun resolveManifest(): VersionManifest {
    return Json.decodeFromString<VersionManifest>(
        get(
            META_MANIFEST
        ) ?: throw IllegalStateException("Failed to get manifest. All is over."))
}

internal fun setupMinecraft(version: VersionDefinition, project: Project) {



}