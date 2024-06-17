package org.example.kotlin_demo.service

import org.example.kotlin_demo.api.auth.request.SignInRequest
import org.example.kotlin_demo.api.auth.request.SignUpRequest
import org.example.kotlin_demo.config.security.JwtService
import org.example.kotlin_demo.exception.BusinessException
import org.example.kotlin_demo.exception.ErrorCode
import org.example.kotlin_demo.model.api.JwtTokenResponse
import org.example.kotlin_demo.model.convertor.MemberConvertor
import org.example.kotlin_demo.model.entity.MemberEntity
import org.example.kotlin_demo.model.entity.MemberSignInHistoryEntity
import org.example.kotlin_demo.repository.MemberRepository
import org.example.kotlin_demo.repository.MemberSignInHistoryRepository
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class AuthService(
  private val memberRepository: MemberRepository,
  private val passwordEncoder: PasswordEncoder,
  private val authenticationManager: AuthenticationManager,
  private val jwtService: JwtService,
  private val memberSignInHistoryRepository: MemberSignInHistoryRepository,
) {

  fun signUp(request: SignUpRequest) {

    validDuplicateMemberByEmail(request.email)
    val memberEntity = with(request) {
      MemberEntity.create(
        email = email,
        password = passwordEncoder.encode(password),
        regNo = regNo,
        role = role
      )
    }
    memberRepository.save(memberEntity)
  }

  fun signIn(request: SignInRequest): JwtTokenResponse {

    authenticationManager.authenticate(
      UsernamePasswordAuthenticationToken(
        request.email,
        request.password
      )
    )

    var memberEntity = memberRepository.findByEmail(request.email)?.apply {
      recordSignInHistory(this)
    } ?: throw BusinessException(ErrorCode.MEMBER_NOT_FOUND)

    var member = MemberConvertor.toDomain(memberEntity)
    var accessToken = jwtService.createAccessToken(member)

//    return JwtTokenResponse.of(
//      accessToken = jwtService.createAccessToken(MemberConvertor.toDomain(memberEntity))
//    )

    return JwtTokenResponse.of(
      accessToken = accessToken
    )
  }

  private fun validDuplicateMemberByEmail(email: String) {
    if (memberRepository.existsByEmail(email)) throw BusinessException(ErrorCode.MEMBER_ALREADY_EXISTS)
  }

  private fun recordSignInHistory(memberEntity: MemberEntity) {
    val memberSignInHistoryEntity = MemberSignInHistoryEntity.create(
      email = memberEntity.email,
      memberEntity = memberEntity
    )
    memberSignInHistoryRepository.save(memberSignInHistoryEntity)
  }
}