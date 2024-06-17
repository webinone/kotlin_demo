package org.example.kotlin_demo.api.advice

import io.github.oshai.kotlinlogging.KotlinLogging
import org.example.kotlin_demo.exception.BusinessException
import org.example.kotlin_demo.exception.ErrorCode
import org.example.kotlin_demo.model.api.ErrorResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice(basePackages = arrayOf("org.example.kotlin_demo.api"))
class ExceptionAdvice {

  private val logger = KotlinLogging.logger {}

  @ExceptionHandler(MethodArgumentNotValidException::class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  fun handleMethodArgumentNotValidException(e: MethodArgumentNotValidException): ErrorResponse {
    return ErrorResponse.of(ErrorCode.SCHEMA_VALIDATION_ERROR, e.message)
  }

  @ExceptionHandler(BusinessException::class)
  fun handleBusinessException(e: BusinessException): ResponseEntity<ErrorResponse> {
    val errorResponse: ErrorResponse = ErrorResponse.of(e.errorCode, e.message)
    return ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.valueOf(e.errorCode.statusCode))
  }

  @ExceptionHandler(Exception::class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  fun handleException(e: Exception): ErrorResponse {
    return ErrorResponse.of(ErrorCode.INTERNAL_SERVER_ERROR, e.message)
  }
}