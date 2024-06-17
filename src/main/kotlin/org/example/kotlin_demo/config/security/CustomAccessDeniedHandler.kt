package org.example.kotlin_demo.config.security

import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.example.kotlin_demo.exception.ErrorCode
import org.example.kotlin_demo.model.api.ApiResponse
import org.example.kotlin_demo.model.api.ErrorResponse
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.web.access.AccessDeniedHandler
import org.springframework.stereotype.Component

@Component
class CustomAccessDeniedHandler(
  private val objectMapper: ObjectMapper,
) : AccessDeniedHandler {

  override fun handle(
    request: HttpServletRequest?,
    response: HttpServletResponse?,
    accessDeniedException: AccessDeniedException?
  ) {

    val errorResponse: ErrorResponse =
      ErrorResponse.of(ErrorCode.ACCESS_DENIED, accessDeniedException!!.message).also {
        ApiResponse.error(this)
      }

    val responseBody = objectMapper.writeValueAsString(errorResponse)
    response!!.contentType = MediaType.APPLICATION_JSON_VALUE
    response!!.status = HttpStatus.FORBIDDEN.value()
    response!!.characterEncoding = "UTF-8"
    response!!.writer.write(responseBody)
  }

}