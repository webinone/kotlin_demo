package org.example.kotlin_demo.api.auth.request

import jakarta.validation.constraints.NotBlank

data class SignInRequest(
  @field:NotBlank
  val email: String,
  @field:NotBlank
  val password: String,
) {

  fun validate() {
    // TODO :
  }
}
