package com.gcc.bankapplication.exception

import com.fasterxml.jackson.databind.exc.InvalidFormatException
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import javax.persistence.EntityNotFoundException

@ControllerAdvice
class GlobalExceptionHandler {

    private val logger = LoggerFactory.getLogger(GlobalExceptionHandler::class.java)

    @ExceptionHandler(HttpMessageNotReadableException::class)
    fun handleHttpMessageNotReadableException(e: HttpMessageNotReadableException): ResponseEntity<ErrorResponse> {
        logger.info("Handling http message not readable", e)

        when{
            e.cause is InvalidFormatException -> {
                return ResponseEntity<ErrorResponse>(
                    ErrorResponse("Invalid date format - must be yyyy-MM-dd"),
                    HttpStatus.BAD_REQUEST
                )
            }
            else -> {
                return ResponseEntity<ErrorResponse>(
                    ErrorResponse("Http Message Not Readable Exception"),
                    HttpStatus.BAD_REQUEST
                )
            }
        }
    }

    @ExceptionHandler(EntityNotFoundException::class)
    fun handleEntityNotFoundException(e: EntityNotFoundException): ResponseEntity<ErrorResponse> {
      logger.info("Handling not found entity exception", e)

      return ResponseEntity<ErrorResponse>(
          ErrorResponse("Object Not found"),
          HttpStatus.NOT_FOUND
      )
    }

    @ExceptionHandler(JpaObjectRetrievalFailureException::class)
    fun handleJpaObjectRetrievalFailureException(e: JpaObjectRetrievalFailureException): ResponseEntity<ErrorResponse> {
        logger.info("Handling Jpa Object Retieval Failure Exception", e)

        when{
            e.cause is EntityNotFoundException -> {
                return ResponseEntity<ErrorResponse>(
                    ErrorResponse("Object Not found"),
                    HttpStatus.NOT_FOUND
                )
            }
        }

        return ResponseEntity<ErrorResponse>(
            ErrorResponse("Object not found"),
            HttpStatus.NOT_FOUND
        )
    }

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