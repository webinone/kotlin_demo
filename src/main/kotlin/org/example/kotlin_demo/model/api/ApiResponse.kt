package org.example.kotlin_demo.model.api

import com.fasterxml.jackson.annotation.JsonInclude
import java.time.LocalDateTime

data class ApiResponse<T>(
  val timestamp: LocalDateTime = LocalDateTime.now(),
  val status: String,
  @JsonInclude(JsonInclude.Include.NON_NULL)
  val message: String? = null,
  @JsonInclude(JsonInclude.Include.NON_NULL)
  val data: T? = null,
) {

  companion
  object {

    private const val SUCCESS = "success"
    private const val ERROR = "error"

    fun <T> ok(data: T): ApiResponse<T> {
      return ApiResponse(
        status = SUCCESS,
        data = data
      )
    }

    fun <T> error(data: T): ApiResponse<T> {
      return ApiResponse(
        status = ERROR,
        data = data
      )
    }
  }
}