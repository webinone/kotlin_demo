package org.example.kotlin_demo.config.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
class SecurityConfig(
  val jwtAuthenticationFilter: JwtAuthenticationFilter,
  val customUserDetailsService: CustomUserDetailsService,
  val customAuthenticationEntryPoint: CustomAuthenticationEntryPoint,
  val customAccessDeniedHandler: CustomAccessDeniedHandler,
) {
  private val AUTH_WHITE_LIST = arrayOf("/api/v1/auth/**")

  @Bean
  fun securityFilterChain(http: HttpSecurity): SecurityFilterChain =
    http.csrf { it.disable() }
      .cors { it.disable() }
      .formLogin { it.disable() }
      .authorizeHttpRequests {
        it.requestMatchers(*AUTH_WHITE_LIST).permitAll()
          .anyRequest().authenticated()
      }
      .sessionManagement {
        it.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
      }
      .authenticationProvider(authenticationProvider())
      .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter::class.java)
      .exceptionHandling {
        it.authenticationEntryPoint(customAuthenticationEntryPoint)
          .accessDeniedHandler(customAccessDeniedHandler)
      }
      .build()

  @Bean
  fun authenticationProvider(): AuthenticationProvider =
    DaoAuthenticationProvider().also {
      it.setUserDetailsService(customUserDetailsService)
      it.setPasswordEncoder(passwordEncoder())
    }

  @Bean
  fun authenticationManager(config: AuthenticationConfiguration): AuthenticationManager =
    config.authenticationManager

  @Bean
  fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder()
}