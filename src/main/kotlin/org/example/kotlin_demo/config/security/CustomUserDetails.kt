package org.example.kotlin_demo.config.security

import org.example.kotlin_demo.model.domain.Member
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class CustomUserDetails(
  val member: Member
) : UserDetails {

  override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
    return mutableListOf(SimpleGrantedAuthority("ROLE." + member.role.name))
  }

  override fun getPassword(): String {
    return member.password
  }

  override fun getUsername(): String {
    return member.email
  }

  override fun isAccountNonExpired(): Boolean {
    return super.isAccountNonExpired()
  }

  override fun isAccountNonLocked(): Boolean {
    return super.isAccountNonLocked()
  }

  override fun isCredentialsNonExpired(): Boolean {
    return super.isCredentialsNonExpired()
  }

  override fun isEnabled(): Boolean {
    return super.isEnabled()
  }
}