package org.example.kotlin_demo.service

import com.navercorp.fixturemonkey.FixtureMonkey
import com.navercorp.fixturemonkey.api.introspector.ConstructorPropertiesArbitraryIntrospector
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.AssertionsForClassTypes
import org.example.kotlin_demo.api.auth.request.SignInRequest
import org.example.kotlin_demo.api.auth.request.SignUpRequest
import org.example.kotlin_demo.config.security.JwtService
import org.example.kotlin_demo.exception.BusinessException
import org.example.kotlin_demo.exception.ErrorCode
import org.example.kotlin_demo.model.convertor.MemberConvertor
import org.example.kotlin_demo.model.entity.MemberEntity
import org.example.kotlin_demo.model.enums.RoleType
import org.example.kotlin_demo.repository.MemberRepository
import org.example.kotlin_demo.repository.MemberSignInHistoryRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers.any
import org.mockito.BDDMockito.given
import org.mockito.BDDMockito.then
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import java.util.UUID

@ExtendWith(MockitoExtension::class)
class AuthServiceTest {

  @Mock
  lateinit var jwtService: JwtService

  @Mock
  lateinit var memberRepository: MemberRepository

  @Mock
  lateinit var passwordEncoder: PasswordEncoder

  @Mock
  lateinit var memberSignInHistoryRepository: MemberSignInHistoryRepository

  @Mock
  lateinit var authenticationManager: AuthenticationManager

  @Mock
  lateinit var memberConvertor: MemberConvertor

  @InjectMocks
  lateinit var target: AuthService

  lateinit var fixtureMonkey: FixtureMonkey

  @BeforeEach
  fun setUp() {
    passwordEncoder = BCryptPasswordEncoder()
    fixtureMonkey = FixtureMonkey.builder()
      .objectIntrospector(ConstructorPropertiesArbitraryIntrospector.INSTANCE)
      .build()
  }

  @Test
  fun `회원가입 성공`() {
  }

  @Test
  fun `회원가입_이미같은_이메일이_있는경우`() {

    // given
    val request = fixtureMonkey.giveMeBuilder(SignUpRequest::class.java)
      .set("email", "test@test.com")
      .set("password", "password")
      .set("regNo", "11111")
      .set("role", RoleType.ADMIN)
      .sample();

    given(memberRepository.existsByEmail(request.email)).willThrow(BusinessException(ErrorCode.MEMBER_ALREADY_EXISTS))

    // when
    val throwable = AssertionsForClassTypes.catchThrowable {
      target.signUp(request)
    }

    // then
    assertThat(throwable).isInstanceOf(BusinessException::class.java)
      .hasMessage(ErrorCode.MEMBER_ALREADY_EXISTS.message)
  }

  @Test
  fun `화원_로그인_테스트`() {

    // given
    val request = fixtureMonkey.giveMeBuilder(SignInRequest::class.java)
      .set("email", "test@test.com")
      .set("password", "password")
      .sample();

    val encodedPassword = passwordEncoder.encode(request.password)
    val memberEntity = MemberEntity(
      id = 1L,
      email = request.email,
      password = encodedPassword,
      regNo = "1111",
      role = RoleType.ADMIN
    )

    given(memberRepository.findByEmail(request.email)).willReturn(memberEntity)
    given(jwtService.createAccessToken(MemberConvertor.toDomain(memberEntity))).willReturn(
      encodedPassword
    )

    // when
    val jwtResponse = target.signIn(request)

    // then
    assertThat(jwtResponse.tokenType).isEqualTo("Bearer")
    assertThat(jwtResponse.accessToken).isNotNull()


  }
}