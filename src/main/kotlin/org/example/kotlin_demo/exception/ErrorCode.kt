package org.example.kotlin_demo.exception

enum class ErrorCode(
  val statusCode: Int,
  val message: String,
) {

  MEMBER_NOT_FOUND(404, "Member not found."),
  MEMBER_ALREADY_EXISTS(400, "MEMBER ALREADY EXISTS"),
  UNAUTHORIZED(401, "UNAUTHORIZED"),
  ACCESS_DENIED(403, "ACCESS_DENIED"),
  SCHEMA_VALIDATION_ERROR(400, "SCHEMA VALIDATION ERROR"),
  INTERNAL_SERVER_ERROR(500, "INTERNAL SERVER ERROR"),
  ;

}