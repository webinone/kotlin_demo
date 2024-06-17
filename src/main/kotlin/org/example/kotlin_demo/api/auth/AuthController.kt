package org.example.kotlin_demo.api.auth

import com.fasterxml.jackson.databind.ObjectMapper
import io.github.oshai.kotlinlogging.KotlinLogging
import jakarta.validation.Valid
import org.example.kotlin_demo.api.auth.request.SignInRequest
import org.example.kotlin_demo.api.auth.request.SignUpRequest
import org.example.kotlin_demo.model.api.ApiResponse
import org.example.kotlin_demo.model.api.JwtTokenResponse
import org.example.kotlin_demo.service.AuthService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/auth")
class AuthController(
  private val authService: AuthService,
  private val objectMapper: ObjectMapper,
) {
  private val logger = KotlinLogging.logger {}

  @PostMapping("/signup")
  fun signUp(@Valid @RequestBody request: SignUpRequest) =
    request.validate().run { authService.signUp(request) }


  @PostMapping("/signin")
  fun signIn(@Valid @RequestBody request: SignInRequest): JwtTokenResponse =
    authService.signIn(request)

}