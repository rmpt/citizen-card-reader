package com.rmpt.citizencard.reader.model.builder

import com.rmpt.citizencard.reader.model.CitizenCardData

class CitizenCardDataBuilder {
    private var givenName: String? = null
    private var sureName: String? = null
    private var gender: String? = null
    private var birthDay: String? = null
    private var taxNumber: String? = null
    private var socialSecurityNumber: String? = null
    private var citizenNumber: String? = null
    private var expirationDate: String? = null
    private var picture: String? = null

    fun withGivenName(givenName: String): CitizenCardDataBuilder {
        this.givenName = givenName
        return this
    }

    fun withSureName(sureName: String): CitizenCardDataBuilder {
        this.sureName = sureName
        return this
    }

    fun withGender(gender: String): CitizenCardDataBuilder {
        this.gender = gender
        return this
    }

    fun withBirthDay(birthDay: String): CitizenCardDataBuilder {
        this.birthDay = birthDay
        return this
    }

    fun withTaxNumber(taxNumber: String): CitizenCardDataBuilder {
        this.taxNumber = taxNumber
        return this
    }

    fun withSocialSecurityNumber(socialSecurityNumber: String): CitizenCardDataBuilder {
        this.socialSecurityNumber = socialSecurityNumber
        return this
    }

    fun withCitizenNumber(citizenNumber: String): CitizenCardDataBuilder {
        this.citizenNumber = citizenNumber
        return this
    }

    fun withExpirationDate(expirationDate: String): CitizenCardDataBuilder {
        this.expirationDate = expirationDate
        return this
    }

    fun withPicture(picture: String): CitizenCardDataBuilder {
        this.picture = picture
        return this
    }

    fun build(): CitizenCardData =
        CitizenCardData(
            givenName = givenName,
            sureName = sureName,
            gender = gender,
            birthDay = birthDay,
            taxNumber = taxNumber,
            socialSecurityNumber = socialSecurityNumber,
            citizenNumber = citizenNumber,
            expirationDate = expirationDate,
            picture = picture
        )
}
