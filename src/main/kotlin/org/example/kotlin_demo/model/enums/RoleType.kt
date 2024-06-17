package org.example.kotlin_demo.model.enums

enum class RoleType(
  val description: String
) {

  ADMIN("관리자"),
  MEMBER("일반회원"),
  GUEST("게스트");

}