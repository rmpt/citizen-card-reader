package com.rmpt.citizencard.reader.peidlib

import mu.KotlinLogging
import pt.gov.cartaodecidadao.*;

object PteidlibLoader {

    private val log = KotlinLogging.logger {}

    private var firstLoad = true

    fun load() {
        if (!firstLoad) throw RuntimeException("Pteid lib already loaded. Library should only be laoded once")
        firstLoad = false
        log.info { "Loading PTEID library...." }
        PTEID_ReaderSet.initSDK();
        log.info { "PTEID library has been loaded." }
    }

    fun destroy() {
        log.info { "Destroying pteid library..." }
        try {
            PTEID_ReaderSet.releaseSDK()
            log.info { "Pteid library destroyed." }
        } catch (e: Exception) {
            log.error(e) { "Could not destoy pteidlib." }
        }
    }
}
