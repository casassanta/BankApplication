package com.gcc.bankapplication.exception

import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class GlobalExceptionHandler {

    private val logger = LoggerFactory.getLogger(GlobalExceptionHandler::class.java)

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleMethodArgumentNotValid(e: MethodArgumentNotValidException): ResponseEntity<ErrorResponse>{
        logger.info("Handling invalid arguments exception", e)

        val fieldErrors = e.bindingResult.fieldErrors.map{ error ->
            ErrorResponse.FieldError(
                error.field,
                error.defaultMessage ?: ""
            )
        }

        return ResponseEntity<ErrorResponse>(
            ErrorResponse("Invalid argument(s)", fieldErrors),
            HttpStatus.BAD_REQUEST
        )
    }

    @ExceptionHandler(InvalidAddressesException::class)
    fun handleMissingAddress(e: InvalidAddressesException): ResponseEntity<ErrorResponse> {
        logger.info("Handling missing address exception", e)

        return ResponseEntity<ErrorResponse>(
            ErrorResponse(e.message),
            HttpStatus.BAD_REQUEST
        )
    }


    @ExceptionHandler(JpaObjectRetrievalFailureException::class)
    fun handleJpaObjectRetrievalFailureException(e: JpaObjectRetrievalFailureException): ResponseEntity<ErrorResponse> {
        logger.info("Handling Jpa Object Retieval Failure Exception", e)

        return ResponseEntity<ErrorResponse>(
            ErrorResponse("Object not found"),
            HttpStatus.NOT_FOUND
        )
    }

    @ExceptionHandler(Throwable::class)
    fun handleThrowable(e: Throwable): ResponseEntity<ErrorResponse> {
        logger.error("Handling exception with http status 500", e)

        return ResponseEntity<ErrorResponse>(
            ErrorResponse("Unknown error"),
            HttpStatus.INTERNAL_SERVER_ERROR
        )
    }

}


data class ErrorResponse(
    val message: String,
    val errors: List<FieldError> = emptyList()
){
    data class FieldError(
        val field: String,
        val message: String
    )
}