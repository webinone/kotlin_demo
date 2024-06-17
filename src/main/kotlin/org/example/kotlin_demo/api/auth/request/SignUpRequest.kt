package org.example.kotlin_demo.api.auth.request

import jakarta.validation.constraints.NotBlank
import org.example.kotlin_demo.model.enums.RoleType
import org.jetbrains.annotations.NotNull

data class SignUpRequest(
  @field:NotBlank
  val email: String,
  @field:NotBlank
  val password: String,
  @field:NotBlank
  val regNo: String,
  @field:NotNull
  val role: RoleType
) {

  fun validate() {
    // TODO :
  }

}
