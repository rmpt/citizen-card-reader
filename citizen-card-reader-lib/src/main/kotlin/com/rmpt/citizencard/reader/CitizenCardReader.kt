package com.rmpt.citizencard.reader

import com.rmpt.citizencard.reader.exception.NoCardException
import com.rmpt.citizencard.reader.exception.NoReaderException
import com.rmpt.citizencard.reader.exception.ReadingException
import com.rmpt.citizencard.reader.model.CitizenCardData
import com.rmpt.citizencard.reader.model.CitizenCardField
import com.rmpt.citizencard.reader.model.builder.CitizenCardDataBuilder
import pt.gov.cartaodecidadao.PTEID_EId
import pt.gov.cartaodecidadao.PTEID_ExParamRange
import pt.gov.cartaodecidadao.PTEID_ReaderContext
import pt.gov.cartaodecidadao.PTEID_ReaderSet
import java.util.Base64

class CitizenCardReader {

    private val readerInstance = PTEID_ReaderSet.instance()

    fun getAvailableReaders(): List<String> {
        readerInstance.releaseReaders()
        return readerInstance.readerList().toList()
    }

    fun getCitizenCardDataByReader(reader: String, fields: List<CitizenCardField>): CitizenCardData {
        val cardReader = getReader(reader)
        return if (!cardReader.isCardPresent) throw NoCardException(reader)
        else readData(reader, cardReader.eidCard.id, fields)
    }

    private fun getReader(reader: String): PTEID_ReaderContext =
        try {
            readerInstance.getReaderByName(reader)
        } catch (e: Exception) {
            if (e is PTEID_ExParamRange) {
                readerInstance.releaseReaders()
                readerInstance.flushCache()
            }
            throw NoReaderException(reader)
        }

    private fun readData(reader: String, card: PTEID_EId, fields: List<CitizenCardField>): CitizenCardData =
        try {
            val builder = CitizenCardDataBuilder()
            fields.toSet()
                .forEach {
                    when (it) {
                        CitizenCardField.GivenName -> builder.withGivenName(card.givenName)
                        CitizenCardField.SureName -> builder.withSureName(card.surname)
                        CitizenCardField.Gender -> builder.withGender(card.gender)
                        CitizenCardField.BirthDay -> builder.withBirthDay(card.dateOfBirth)
                        CitizenCardField.TaxNumber -> builder.withTaxNumber(card.taxNo)
                        CitizenCardField.SocialSecurityNumber -> builder.withSocialSecurityNumber(card.socialSecurityNumber)
                        CitizenCardField.CitizenNumber -> builder.withCitizenNumber(card.civilianIdNumber)
                        CitizenCardField.ExpirationDate -> builder.withExpirationDate(card.validityEndDate)
                        CitizenCardField.Picture -> builder.withPicture(
                            Base64.getEncoder().encodeToString(card.photoObj.getphoto().GetBytes())
                        )
                    }
                }
            builder.build()
        } catch (e: Exception) {
            throw ReadingException(reader)
        }
}
