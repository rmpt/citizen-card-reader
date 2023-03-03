package com.rmpt.citizencard.reader.api.reader.controller

import com.rmpt.citizencard.reader.api.common.exception.BadRequestException
import com.rmpt.citizencard.reader.api.reader.service.CitizenCardService
import com.rmpt.citizencard.reader.model.CitizenCardData
import com.rmpt.citizencard.reader.model.CitizenCardField
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/ccard")
class CitizenCardController {

    @Autowired
    private lateinit var citizenCardService: CitizenCardService

    @GetMapping("/readers")
    fun getReaders(): List<String> = citizenCardService.getAvailableReaders()

    @GetMapping("/data")
    fun getDataByReader(
        @RequestParam("reader") cardReaderName: String,
        @RequestParam("fields") fields: List<String>
    ): CitizenCardData =
        citizenCardService.getCitizenCardData(cardReaderName, stringFields2enum(fields))

    private fun stringFields2enum(fields: List<String>) =
        try {
            if (fields.any { it.equals("all", true) }) CitizenCardField.values().toList()
            else fields.map { CitizenCardField.valueOf(it) }
        } catch (e: Exception) {
            val possibleValues = CitizenCardField
                .values()
                .map { it.toString() }
                .plus("All")
                .joinToString()
            throw BadRequestException("You have some invalid citizen card fields. Possible fields are: '$possibleValues'")
        }
}
