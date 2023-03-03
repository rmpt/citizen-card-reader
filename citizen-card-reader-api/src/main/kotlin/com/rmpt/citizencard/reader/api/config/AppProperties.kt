package com.rmpt.citizencard.reader.api.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(prefix = "app")
data class AppProperties(
    val requiredHeader: RequiredHeader?,
    val cors: String?
)

data class RequiredHeader(
    val name: String,
    val value: String
)
