package org.example.kotlin_demo.repository

import com.querydsl.jpa.impl.JPAQueryFactory
import jakarta.persistence.EntityManager
import org.example.kotlin_demo.config.db.QueryDslConfig
import org.example.kotlin_demo.model.enums.RoleType
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.context.annotation.Import
import org.springframework.data.domain.PageRequest
import java.time.LocalDateTime

import org.assertj.core.api.AssertionsForInterfaceTypes.assertThat
import org.springframework.test.context.ActiveProfiles


@ActiveProfiles("test")
@Import(QueryDslConfig::class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class MemberSignInHistoryRepositoryTest {

  @Autowired
  lateinit var entityManager: TestEntityManager

  @Autowired
  lateinit var memberSignInHistoryRepository: MemberSignInHistoryRepository

  lateinit var em: EntityManager

  lateinit var jpaQueryFactory: JPAQueryFactory

  @BeforeEach
  fun setUp() {
    em = entityManager.entityManager
    jpaQueryFactory = JPAQueryFactory(em)
  }

  @Test
  fun `findSignInHistoryByCriteriaTest`() {

    val startDateTime = LocalDateTime.of(2023, 1, 1, 0, 0)
    val endDateTime = LocalDateTime.of(2023, 12, 31, 23, 59)

    var list = memberSignInHistoryRepository.findSearchCriteria(
      1L, RoleType.ADMIN, null, null,
      PageRequest.of(0, 2)
    );

//    assertThat(list).hasSize(2)
  }


}
