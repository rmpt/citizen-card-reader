package com.rmpt.citizencard.reader.peidlib.setup

import com.rmpt.citizencard.reader.peidlib.PteidlibLoader
import java.io.File
import java.nio.file.Files
import java.nio.file.StandardCopyOption

abstract class PteidSetup {

    abstract fun run(): String

    protected fun createTempDir(): File {
        val tempDir = Files.createTempDirectory("citizen-card-native-lib").toFile()
        tempDir.deleteOnExit()
        return tempDir
    }

    protected fun createTempEmptyFile(dir: File, fileName: String): File {
        val emptyTempFile = File(dir, fileName)
        emptyTempFile.deleteOnExit()
        return emptyTempFile
    }

    protected fun copyResourceFileToSystemFile(fromResourceFilePath: String, toSystemFile: File) {
        val fileUrl = PteidlibLoader::class.java.getResource(fromResourceFilePath)!!
        fileUrl.openStream().use { Files.copy(it, toSystemFile.toPath(), StandardCopyOption.REPLACE_EXISTING) }
    }
}
