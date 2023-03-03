package com.rmpt.citizencard.reader.api.common.exception

import com.rmpt.citizencard.reader.api.common.dto.ApiError
import com.rmpt.citizencard.reader.exception.NoCardException
import com.rmpt.citizencard.reader.exception.NoReaderException
import com.rmpt.citizencard.reader.exception.ReadingException
import mu.KotlinLogging
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@ControllerAdvice
class ExceptionHandler : ResponseEntityExceptionHandler() {

    private val log = KotlinLogging.logger {}

    @ExceptionHandler(value = [BadRequestException::class])
    fun handleBadRequestException(ex: BadRequestException): ResponseEntity<Any> =
        ResponseEntity(ex.message, HttpStatus.BAD_REQUEST)

    @ExceptionHandler(value = [NoReaderException::class])
    fun handleCardRearException(ex: NoReaderException): ResponseEntity<ApiError> =
        ResponseEntity
            .status(HttpStatus.BAD_REQUEST.value())
            .body(
                ApiError(
                    code = "no_reader_found",
                    description = "Could not find the given reader: '${ex.cardReaderName}'"
                )
            )

    @ExceptionHandler(value = [NoCardException::class])
    fun handleNoCardException(ex: NoCardException): ResponseEntity<ApiError> =
        ResponseEntity
            .status(HttpStatus.BAD_REQUEST.value())
            .body(
                ApiError(
                    code = "no_card_found",
                    description = "Could not find a card in the given reader: '${ex.cardReaderName}'"
                )
            )

    @ExceptionHandler(value = [ReadingException::class])
    fun handleReadingCardException(ex: ReadingException): ResponseEntity<ApiError> =
        ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
            .body(
                ApiError(
                    code = "error_reading_data",
                    description = "Some error occurred during data reading from reader: '${ex.cardReaderName}'"
                )
            )

    @ExceptionHandler(value = [Exception::class])
    fun handleGenericException(ex: Exception): ResponseEntity<ApiError> {
        log.error(ex) { "Unexpected exception occurred, reply with 500." }
        return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
            .body(
                ApiError(
                    code = "unexpected_error",
                    description = "Some unexpected error occurred processing reading request."
                )
            )
    }
}
