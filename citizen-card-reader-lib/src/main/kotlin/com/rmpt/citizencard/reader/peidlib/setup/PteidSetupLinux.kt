package com.rmpt.citizencard.reader.peidlib.setup

import com.rmpt.citizencard.reader.peidlib.PteidlibLoader
import mu.KotlinLogging
import java.io.File
import java.net.URI
import java.nio.file.FileSystems
import java.nio.file.Files
import kotlin.io.path.isDirectory

/**
 * Copy required files into local directories and keep them for future runs.
 * Unlike MacOs or Windows (that requires only one lib file to run), Linux requires a bunch of libs in specific
 * locations, so we copy and keep them for future runs.
 */
class PteidSetupLinux : PteidSetup() {

    private val log = KotlinLogging.logger {}

    private val rootDir = "/pteid/lin"
    private val libsDir = "$rootDir/libs"
    private val certsDir = "$rootDir/certs"

    private val targetLibsDir = "/usr/lib"
    private val targetCertsDir = "/usr/local/share/certs"

    private val mainLibFilePath = "/usr/lib/libpteidlibj.so.2.0.0"

    override fun run(): String {
        if (mainLibFileAlreadyExists()) {
            log.info { "Seems that required files are already in place, skip copy files step." }
            return mainLibFilePath
        }
        log.info { "Copying lib files from '$libsDir' to '$targetLibsDir'..." }
        copyResourceDirectoryToSystem(libsDir, targetLibsDir)

        log.info { "Copying certificate files from '$certsDir' to '$targetCertsDir'..." }
        copyResourceDirectoryToSystem(certsDir, targetCertsDir)
        return mainLibFilePath
    }

    private fun mainLibFileAlreadyExists() = File(mainLibFilePath).exists()

    private fun copyResourceDirectoryToSystem(fromResourceDirPath: String, toSystemDir: String) {
        val destinationDir = File(toSystemDir)
        if (!destinationDir.exists()) {
            destinationDir.mkdirs()
        }

        val jarPath = PteidlibLoader::class.java.protectionDomain.codeSource.location
            .path.split("!")[0]
        val jarUri = URI("jar:$jarPath")
        FileSystems.newFileSystem(jarUri, emptyMap<String, Any>()).use {
            val fromDir = it.getPath("BOOT-INF/classes/$fromResourceDirPath")
            Files.find(fromDir, 1, { _, _ -> true }).use { s ->
                s.forEach { path ->
                    if (!path.isDirectory()) {
                        val fileName = path.fileName.toString()
                        val destinationFile = File(toSystemDir, fileName)
                        val fileAbsPath = path.toAbsolutePath().toString()
                        copyResourceFileToSystemFile(fileAbsPath, destinationFile)
                        log.info { "File '${path.toAbsolutePath()}' copied to '${destinationFile.absolutePath}'" }
                    }
                }
            }
        }
    }
}
