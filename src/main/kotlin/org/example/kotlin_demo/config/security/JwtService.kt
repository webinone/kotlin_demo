package org.example.kotlin_demo.config.security

import io.github.oshai.kotlinlogging.KotlinLogging
import io.jsonwebtoken.*
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import io.jsonwebtoken.security.SecurityException
import org.example.kotlin_demo.model.domain.Member
import org.springframework.stereotype.Service
import java.security.Key
import java.time.ZonedDateTime
import java.util.*

@Service
class JwtService(
  val jwtProperties: JwtProperties
) {

  private val logger = KotlinLogging.logger {}
  lateinit var key: Key

  init {
    val keyBytes = Decoders.BASE64.decode(jwtProperties.key)
    this.key = Keys.hmacShaKeyFor(keyBytes)
  }

  /**
   * Access Token 생성
   * @param member
   * @return Access Token String
   */
  fun createAccessToken(member: Member): String {
    val token = createToken(member, jwtProperties.accessTokenExpiration)
//    return createToken(member, jwtProperties.accessTokenExpiration)
    println("token : ${token}")
    return token
  }


  /**
   * JWT 생성
   * @param member
   * @param expireTime
   * @return JWT String
   */
  private fun createToken(member: Member, expireTime: Long): String {
    val claims = Jwts.claims()
      .subject(member.email)
      .add("id", member.id)
      .add("email", member.email)
      .add("role", member.role)
      .build()

    val now = ZonedDateTime.now()
    val tokenValidity = now.plusSeconds(expireTime)

    return Jwts.builder()
      .signWith(key, SignatureAlgorithm.HS256)
      .setClaims(claims)
      .setIssuedAt(Date.from(now.toInstant()))
      .setExpiration(Date.from(tokenValidity.toInstant()))
      .compact()
  }


  /**
   * Token에서 User ID 추출
   * @param token
   * @return User ID
   */
  fun getUserId(token: String?): Long {
    return parseClaims(token).get("memberId", Long::class.java)
  }

  fun getEmail(token: String?): String {
    return parseClaims(token).get("email", String::class.java)
  }


  /**
   * JWT 검증
   * @param token
   * @return IsValidate
   */
  fun validateToken(token: String?): Boolean {
    try {
      Jwts.parser().setSigningKey(key).build().parseClaimsJws(token)
      return true
    } catch (e: SecurityException) {
      logger.info { "Invalid JWT Token ${e}" }
    } catch (e: MalformedJwtException) {
      logger.info { "Invalid JWT Token ${e}" }
    } catch (e: ExpiredJwtException) {
      logger.info { "Expired JWT Token ${e}" }
    } catch (e: UnsupportedJwtException) {
      logger.info { "Unsupported JWT Token ${e}" }
    } catch (e: IllegalArgumentException) {
      logger.info { "JWT claims string is empty. ${e}" }
    }
    return false
  }


  /**
   * JWT Claims 추출
   * @param accessToken
   * @return JWT Claims
   */
  fun parseClaims(accessToken: String?): Claims {
    return try {
      Jwts.parser().setSigningKey(key).build().parseClaimsJws(accessToken).body
    } catch (e: ExpiredJwtException) {
      e.claims
    }
  }
}