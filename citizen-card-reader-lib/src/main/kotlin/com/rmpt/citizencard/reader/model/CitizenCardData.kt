package com.rmpt.citizencard.reader.model

data class CitizenCardData(
    val givenName: String?,
    val sureName: String?,
    val gender: String?,
    val birthDay: String?,
    val taxNumber: String?,
    val socialSecurityNumber: String?,
    val citizenNumber: String?,
    val expirationDate: String?,
    val picture: String?
)
