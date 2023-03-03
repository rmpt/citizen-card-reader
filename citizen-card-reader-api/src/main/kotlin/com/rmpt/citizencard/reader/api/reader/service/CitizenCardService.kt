package com.rmpt.citizencard.reader.api.reader.service

import com.rmpt.citizencard.reader.CitizenCardReader
import com.rmpt.citizencard.reader.model.CitizenCardField
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class CitizenCardService {

    @Autowired
    private lateinit var citizenCardReader: CitizenCardReader

    @Synchronized fun getAvailableReaders(): List<String> = citizenCardReader.getAvailableReaders()

    @Synchronized fun getCitizenCardData(cardReaderName: String, fields: List<CitizenCardField>) =
        citizenCardReader.getCitizenCardDataByReader(cardReaderName, fields)
}
