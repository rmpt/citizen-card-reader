package com.rmpt.citizencard.reader.peidlib.setup

import mu.KotlinLogging

class PteidSetupMacOs : PteidSetup() {

    private val log = KotlinLogging.logger {}

    private val libFilPath = "/usr/local/lib/libpteidlibj.2.0.0.dylib"

    override fun run(): String {
        libExistsOrThrow(libFilPath)
        log.info { "Using installed lib '$libFilPath'" }
        return libFilPath
    }
}
