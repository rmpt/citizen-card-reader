package com.rmpt.citizencard.reader.peidlib.setup

import com.rmpt.citizencard.reader.peidlib.PteidlibInfo
import com.rmpt.citizencard.reader.peidlib.PteidlibLoader
import mu.KotlinLogging
import java.io.File
import java.nio.file.Files
import java.nio.file.StandardCopyOption

abstract class PteidSetup {

    private val log = KotlinLogging.logger {}

    abstract fun run(): String

    protected fun createTempDir(): File {
        val tempDir = Files.createTempDirectory("citizen-card-native-lib").toFile()
        tempDir.deleteOnExit()
        return tempDir
    }

    protected fun createEmptyFile(dir: File, fileName: String, deleteOnExit: Boolean = true): File {
        val emptyTempFile = File(dir, fileName)
        if (deleteOnExit) {
            emptyTempFile.deleteOnExit()
        }
        return emptyTempFile
    }

    protected fun copyResourceFileToSystemFile(fromResourceFilePath: String, toSystemFile: File) {
        val fileUrl = PteidlibLoader::class.java.getResource(fromResourceFilePath)!!
        fileUrl.openStream().use { Files.copy(it, toSystemFile.toPath(), StandardCopyOption.REPLACE_EXISTING) }
    }

    protected fun libExistsOrThrow(libFilPath: String) {
        if (!File(libFilPath).exists()) {
            val errorMsg = "Could not load pteid library. Expecting to find it at '$libFilPath'. Please install " +
                "${PteidlibInfo.appName} from '${PteidlibInfo.installUrl}'"
            log.warn { errorMsg }
            throw RuntimeException(errorMsg)
        }
    }
}
