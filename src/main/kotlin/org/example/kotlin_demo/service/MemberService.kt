package org.example.kotlin_demo.service

import org.example.kotlin_demo.common.extension.toPageResponse
import org.example.kotlin_demo.exception.BusinessException
import org.example.kotlin_demo.exception.ErrorCode
import org.example.kotlin_demo.model.api.PageResponse
import org.example.kotlin_demo.model.convertor.MemberConvertor
import org.example.kotlin_demo.model.domain.Member
import org.example.kotlin_demo.model.domain.MemberSignInHistory
import org.example.kotlin_demo.model.enums.RoleType
import org.example.kotlin_demo.repository.MemberRepository
import org.example.kotlin_demo.repository.MemberSignInHistoryRepository
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class MemberService(
  private val memberRepository: MemberRepository,
  private val memberSignInHistoryRepository: MemberSignInHistoryRepository
) {

  fun readMember(id: Long): Member {
    return memberRepository.findByIdOrNull(id)?.run {
      MemberConvertor.toDomain(this)
    } ?: throw BusinessException(ErrorCode.MEMBER_NOT_FOUND)
  }

  fun readMemberSignInHistory(
    id: Long,
    role: RoleType,
    startDateTime: LocalDateTime,
    endDateTime: LocalDateTime,
    pageable: Pageable
  ): PageResponse<MemberSignInHistory> {

    return memberSignInHistoryRepository.findSearchCriteria(
      id,
      role,
      startDateTime,
      endDateTime,
      pageable
    ).run {
      this.toPageResponse()
    }
  }

}
