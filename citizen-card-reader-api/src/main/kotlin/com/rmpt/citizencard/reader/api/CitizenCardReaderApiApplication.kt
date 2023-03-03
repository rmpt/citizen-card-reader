package com.rmpt.citizencard.reader.api

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@SpringBootApplication
@ConfigurationPropertiesScan
open class CitizenCardReaderApiApplication

fun main(args: Array<String>) {
    runApplication<CitizenCardReaderApiApplication>(*args)
}
