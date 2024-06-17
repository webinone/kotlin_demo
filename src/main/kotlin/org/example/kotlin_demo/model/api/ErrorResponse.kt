package org.example.kotlin_demo.model.api

import org.example.kotlin_demo.exception.ErrorCode

data class ErrorResponse(
  val statusCode: Int,
  val message: String?
) {

  companion object {
    fun of(errorCode: ErrorCode): ErrorResponse {
      return of(errorCode, errorCode.message)
    }

    fun of(errorCode: ErrorCode, message: String?): ErrorResponse {
      return ErrorResponse(
        statusCode = errorCode.statusCode,
        message = message
      )
    }
  }
}