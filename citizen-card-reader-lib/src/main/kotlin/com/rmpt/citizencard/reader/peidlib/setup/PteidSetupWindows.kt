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
        val archSubDir = if(isX64()) "x64/" else "x86/"
        copyResourceFileToSystemFile("$rootDir$archSubDir$libFile", nativeLibTmpFile)
        log.info { "Main lib '$libFile' copied to '${nativeLibTmpFile.absolutePath}'" }
        return nativeLibTmpFile.absolutePath
    }

    private fun isX64(): Boolean {
        val arch = System.getenv("PROCESSOR_ARCHITECTURE")
        val arch64 = System.getenv("PROCESSOR_ARCHITEW6432")
        return (arch != null && arch.endsWith("64")) || (arch64 != null && arch64.endsWith("64"))
    }
}
