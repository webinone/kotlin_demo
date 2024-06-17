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
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import java.util.UUID

@ExtendWith(MockitoExtension::class)
class AuthServiceMockkTest {

  
}