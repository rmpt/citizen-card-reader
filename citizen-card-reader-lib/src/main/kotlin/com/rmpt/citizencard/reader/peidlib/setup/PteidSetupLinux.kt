package com.rmpt.citizencard.reader.peidlib.setup

import mu.KotlinLogging

class PteidSetupLinux : PteidSetup() {

    private val log = KotlinLogging.logger {}

    private val libFilPath = "/usr/local/lib/libpteidlibj.so.2.0.0"
    override fun run(): String {
        libExistsOrThrow(libFilPath)
        log.info { "Using installed lib '$libFilPath'" }
        return libFilPath
    }
}
