package com.rmpt.citizencard.reader.exception

class NoReaderException(
    val cardReaderName: String
) : RuntimeException()
