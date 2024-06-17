package org.example.kotlin_demo.api.advice

import org.example.kotlin_demo.model.api.ApiResponse
import org.example.kotlin_demo.model.api.ErrorResponse
import org.springframework.core.MethodParameter
import org.springframework.http.MediaType
import org.springframework.http.converter.HttpMessageConverter
import org.springframework.http.server.ServerHttpRequest
import org.springframework.http.server.ServerHttpResponse
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice

@RestControllerAdvice(basePackages = arrayOf("org.example.kotlin_demo.api"))
class ResponseWrapperAdvice : ResponseBodyAdvice<Any> {

  override fun supports(
    returnType: MethodParameter,
    converterType: Class<out HttpMessageConverter<*>>
  ): Boolean {
    return true
  }

  override fun beforeBodyWrite(
    body: Any?,
    returnType: MethodParameter,
    selectedContentType: MediaType,
    selectedConverterType: Class<out HttpMessageConverter<*>>,
    request: ServerHttpRequest,
    response: ServerHttpResponse
  ): Any? =
    when (body) {
      is ApiResponse<*> -> body
      is ErrorResponse -> ApiResponse.error(body)
      else -> ApiResponse.ok(body)
    }

}