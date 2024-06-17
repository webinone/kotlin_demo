package org.example.kotlin_demo.config.security

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.apache.commons.lang3.StringUtils
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.util.ObjectUtils
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JwtAuthenticationFilter(

  private val customUserDetailsService: CustomUserDetailsService,
  private val jwtService: JwtService,

  ) : OncePerRequestFilter() {

  companion object {
    const val AUTH_HEADER: String = "Authorization"
    const val TOKEN_PREFIX: String = "Bearer "
  }

  override fun doFilterInternal(
    request: HttpServletRequest,
    response: HttpServletResponse,
    filterChain: FilterChain
  ) {

    val authHeader = request.getHeader(AUTH_HEADER)
    var token: String

    if (authHeader.doesNotContanBearerToken()) {
      filterChain.doFilter(request, response)
      return
    }

    token = authHeader.extractTokenValue()
    if (jwtService.validateToken(token)) {
      val userDetails = customUserDetailsService.loadUserByUsername(jwtService.getEmail(token))

      if (!ObjectUtils.isEmpty(userDetails)) {
        val authToken = UsernamePasswordAuthenticationToken(
          userDetails,
          null, userDetails.getAuthorities()
        )

        SecurityContextHolder.getContext().authentication = authToken
      }
    }
    filterChain.doFilter(request, response)
  }

  private fun String?.doesNotContanBearerToken(): Boolean =
    StringUtils.isEmpty(this) || !StringUtils.startsWith(this, TOKEN_PREFIX)

  private fun String.extractTokenValue(): String =
    this.substring(7)

}