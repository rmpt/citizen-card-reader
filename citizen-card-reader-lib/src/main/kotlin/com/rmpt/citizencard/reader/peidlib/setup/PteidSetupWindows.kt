package com.rmpt.citizencard.reader.peidlib.setup

import mu.KotlinLogging

/**
 * Windows works in s slightly different way, so we copy the main lib file into local temp directory and destroy it at
 * shutdown.
 */
class PteidSetupWindows : PteidSetup() {

    private val log = KotlinLogging.logger {}

    private val rootDir = "/pteid/win/"
    private val libFile = "pteidlibj.dll"

    override fun run(): String {
        val tempDir = createTempDir()
        val nativeLibTmpFile = createEmptyFile(tempDir, libFile, true)
        copyResourceFileToSystemFile("$rootDir$libFile", nativeLibTmpFile)
        log.info { "Main lib '$libFile' copied to '${nativeLibTmpFile.absolutePath}'" }
        return nativeLibTmpFile.absolutePath
    }
}
