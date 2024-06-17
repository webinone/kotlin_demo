package org.example.kotlin_demo.config.security

import org.example.kotlin_demo.model.convertor.MemberConvertor
import org.example.kotlin_demo.model.domain.Member
import org.example.kotlin_demo.repository.MemberRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class CustomUserDetailsService(
  private val memberRepository: MemberRepository
) : UserDetailsService {

  override fun loadUserByUsername(username: String): UserDetails {

    val user = memberRepository.findByEmail(username)
      ?.let(MemberConvertor::toDomain)
      ?: throw UsernameNotFoundException(username)

    return CustomUserDetails(user)
  }

}