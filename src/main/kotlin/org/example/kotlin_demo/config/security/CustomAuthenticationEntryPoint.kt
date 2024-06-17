package org.example.kotlin_demo.config.security

import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.example.kotlin_demo.exception.ErrorCode
import org.example.kotlin_demo.model.api.ApiResponse
import org.example.kotlin_demo.model.api.ErrorResponse
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component

@Component
class CustomAuthenticationEntryPoint(
  private val objectMapper: ObjectMapper,
) : AuthenticationEntryPoint {

  override fun commence(
    request: HttpServletRequest?,
    response: HttpServletResponse?,
    authException: AuthenticationException?
  ) {

    val errorResponse: ErrorResponse =
      ErrorResponse.of(ErrorCode.UNAUTHORIZED, authException!!.message).also {
        ApiResponse.error(this)
      }

    val responseBody = objectMapper.writeValueAsString(errorResponse)

    response!!.contentType = MediaType.APPLICATION_JSON_VALUE
    response!!.status = HttpStatus.UNAUTHORIZED.value()
    response!!.characterEncoding = "UTF-8"
    response!!.writer.write(responseBody)
  }
}