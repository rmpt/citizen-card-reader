package com.rmpt.citizencard.reader.peidlib.setup

import mu.KotlinLogging

object PteidSetupFactory {

    private val log = KotlinLogging.logger {}

    fun new(): PteidSetup {
        val os = System.getProperty("os.name")
        return if (os.contains("win", true)) {
            log.info { "Windows Operating System detected" }
            PteidSetupWindows()
        } else if (os.contains("mac", true)) {
            log.info { "MacOs Operating System detected" }
            PteidSetupMacOs()
        } else if (listOf("nix", "aix", "nux").any { os.contains(it, true) }) {
            log.info { "Linux Operating System detected" }
            PteidSetupLinux()
        } else {
            throw RuntimeException("Unknown Operating System: '$os'")
        }
    }
}
