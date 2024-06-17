package org.example.kotlin_demo.model.api

data class JwtTokenResponse(
  val tokenType: String,
  val accessToken: String,
) {

  companion object {
    const val TOKEN_TYPE = "Bearer"

    fun of(accessToken: String): JwtTokenResponse {
      return JwtTokenResponse(
        tokenType = TOKEN_TYPE,
        accessToken = accessToken
      )
    }
  }
}