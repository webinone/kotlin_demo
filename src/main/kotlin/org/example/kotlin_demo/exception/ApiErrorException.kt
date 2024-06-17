package org.example.kotlin_demo.exception

class ApiErrorException : RuntimeException {
  val errorCode: ErrorCode
  lateinit var args: Array<Any>
    private set

  constructor(errorCode: ErrorCode) : super(errorCode.message) {
    this.errorCode = errorCode
  }

  constructor(errorCode: ErrorCode, message: String?) : super(message) {
    this.errorCode = errorCode
  }

  constructor(errorCode: ErrorCode, args: Array<Any>) : super(errorCode.message) {
    this.errorCode = errorCode
    this.args = args
  }

  constructor(errorCode: ErrorCode, message: String?, args: Array<Any>) : super(message) {
    this.errorCode = errorCode
    this.args = args
  }
}
