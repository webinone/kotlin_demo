package org.example.kotlin_demo.model.domain

import org.example.kotlin_demo.model.enums.RoleType
import java.time.LocalDateTime

class MemberSignInHistory(
  val id: Long,
  val email: String,
  val regNo: String,
  val role: RoleType,
  val createdAt: LocalDateTime,
  val updatedAt: LocalDateTime,
) {
}