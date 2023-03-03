package com.rmpt.citizencard.reader.peidlib.setup

import mu.KotlinLogging

/**
 * Copy required files into local temp directory and destroy them at shutdown.
 */
class PteidSetupWindows : PteidSetup() {

    private val log = KotlinLogging.logger {}

    private val libDir = "/pteid/win/"
    private val libFile = "pteidlibj.dll"

    override fun run(): String {
        val tempDir = createTempDir()
        val nativeLibTmpFile = createTempEmptyFile(tempDir, libFile)
        copyResourceFileToSystemFile("$libDir$libFile", nativeLibTmpFile)
        log.info { "Lib file '$libDir$libFile' copied to '${nativeLibTmpFile.absolutePath}'" }
        return nativeLibTmpFile.absolutePath
    }
}
