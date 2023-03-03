package com.rmpt.citizencard.reader.exception

class NoCardException(
    val cardReaderName: String
) : RuntimeException()
