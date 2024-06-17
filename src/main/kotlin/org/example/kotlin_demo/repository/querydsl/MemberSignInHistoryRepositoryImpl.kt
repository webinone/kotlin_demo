package org.example.kotlin_demo.repository.querydsl

import com.querydsl.core.types.Projections
import com.querydsl.jpa.impl.JPAQueryFactory
import org.example.kotlin_demo.common.utils.QuerydslUtils.Companion.eq
import org.example.kotlin_demo.common.utils.QuerydslUtils.Companion.goe
import org.example.kotlin_demo.common.utils.QuerydslUtils.Companion.loe
import org.example.kotlin_demo.model.domain.MemberSignInHistory
import org.example.kotlin_demo.model.entity.QMemberSignInHistoryEntity.memberSignInHistoryEntity
import org.example.kotlin_demo.model.enums.RoleType
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
class MemberSignInHistoryRepositoryImpl(
  private val jpaQueryFactory: JPAQueryFactory
) : MemberSignInHistoryRepositoryCustom {

  override fun findSearchCriteria(
    memberId: Long,
    role: RoleType?,
    startDateTime: LocalDateTime?,
    endDateTime: LocalDateTime?,
    pageable: Pageable,
  ): Page<MemberSignInHistory> {

    val histories: List<MemberSignInHistory> = jpaQueryFactory
      .select(
        Projections.constructor(
          MemberSignInHistory::class.java,
          memberSignInHistoryEntity.id,
          memberSignInHistoryEntity.email,
          memberSignInHistoryEntity.memberEntity.regNo,
          memberSignInHistoryEntity.memberEntity.role,
          memberSignInHistoryEntity.createdAt,
          memberSignInHistoryEntity.updatedAt
        )
      )
      .from(memberSignInHistoryEntity)
      .where(
        eq(memberSignInHistoryEntity.memberEntity.id, memberId),
        eq(memberSignInHistoryEntity.memberEntity.role, role),
        goe(memberSignInHistoryEntity.createdAt, startDateTime),
        loe(memberSignInHistoryEntity.createdAt, endDateTime)
      )
      .offset(pageable.offset)
      .limit(pageable.pageSize.toLong())
      .fetch()

    val total = jpaQueryFactory.selectFrom(memberSignInHistoryEntity)
      .where(
        eq(memberSignInHistoryEntity.memberEntity.id, memberId),
        eq(memberSignInHistoryEntity.memberEntity.role, role),
        goe(memberSignInHistoryEntity.createdAt, startDateTime),
        loe(memberSignInHistoryEntity.createdAt, endDateTime)
      ).fetch().count()

    return PageImpl(histories, pageable, total.toLong())
  }

}