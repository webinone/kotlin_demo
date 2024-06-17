package org.example.kotlin_demo.model.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import org.example.kotlin_demo.model.enums.RoleType

data class Member(
  val id: Long,
  val email: String,
  @JsonIgnore
  val password: String,
  private val regNo: String,
  val role: RoleType,
) {

  companion object {
    fun of(id: Long, email: String, password: String, regNo: String, role: RoleType): Member {
      return Member(
        id = id,
        email = email,
        password = password,
        regNo = regNo,
        role = role
      )
    }
  }
}