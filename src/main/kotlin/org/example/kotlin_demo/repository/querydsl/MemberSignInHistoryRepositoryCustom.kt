package org.example.kotlin_demo.repository.querydsl

import org.example.kotlin_demo.model.domain.MemberSignInHistory
import org.example.kotlin_demo.model.entity.MemberSignInHistoryEntity
import org.example.kotlin_demo.model.enums.RoleType
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import java.time.LocalDateTime

interface MemberSignInHistoryRepositoryCustom {

  fun findSearchCriteria(
    memberId: Long,
    role: RoleType?,
    startDateTime: LocalDateTime?,
    endDateTime: LocalDateTime?,
    pageable: Pageable,
  ): Page<MemberSignInHistory>

}