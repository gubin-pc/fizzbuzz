package me.fizzbuzz.app.game.controller

import com.fasterxml.jackson.databind.exc.InvalidFormatException
import mu.KLogging
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatus.BAD_REQUEST
import org.springframework.http.HttpStatusCode
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler


@RestControllerAdvice
class SimpleControllerAdvice : ResponseEntityExceptionHandler() {
    @ExceptionHandler(IllegalArgumentException::class)
    fun illegalArgumentExceptionHandler(ex: IllegalArgumentException, request: WebRequest?): ResponseEntity<Any>? {
        val status = BAD_REQUEST
        logger.warn(ex.message, ex)
        return handleExceptionInternal(ex, ErrorMessage(ex.message), HttpHeaders(), status, request!!)
    }

    override fun handleHttpMessageNotReadable(
        ex: HttpMessageNotReadableException,
        headers: HttpHeaders,
        status: HttpStatusCode,
        request: WebRequest
    ): ResponseEntity<Any>? {
        val newStatus = BAD_REQUEST
        logger.warn(ex.message, ex)
        val errorMessage = ex
            .takeIf { it.cause is InvalidFormatException }
            ?.let { it.cause as InvalidFormatException}
            ?.let { "Input error: '${it.value}' not a valid number" }
            ?: ex.message
        return handleExceptionInternal(ex, ErrorMessage(errorMessage), headers, newStatus, request)
    }

    @ExceptionHandler(Exception::class)
    fun commonExceptionHandler(ex: Exception, request: WebRequest?): ResponseEntity<Any?>? {
        val status = HttpStatus.INTERNAL_SERVER_ERROR
        logger.error(ex.message, ex)
        return handleExceptionInternal(ex, ErrorMessage(ex.message), HttpHeaders(), status, request!!)
    }

    data class ErrorMessage(val error: String?)
    private companion object: KLogging()
}