package org.example.kotlin_demo.api.member

import org.example.kotlin_demo.config.security.CustomUserDetails
import org.example.kotlin_demo.model.api.PageResponse
import org.example.kotlin_demo.model.domain.Member
import org.example.kotlin_demo.model.domain.MemberSignInHistory
import org.example.kotlin_demo.service.MemberService
import org.springframework.data.domain.PageRequest
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDateTime

@RestController
@RequestMapping("/api/v1/members")
class MemberController(
  private val memberService: MemberService
) {

  @GetMapping("/me")
  fun readMember(@AuthenticationPrincipal customUserDetails: CustomUserDetails): Member =
    memberService.readMember(customUserDetails.member.id)

  @GetMapping("/me/signin")
  fun readMemberSignInHisotry(
    @AuthenticationPrincipal customUserDetails: CustomUserDetails,
    @RequestParam(required = false) startDateTime: LocalDateTime,
    @RequestParam(required = false) endDateTime: LocalDateTime,
    @RequestParam(value = "pageSize") pageSize: Int,
    @RequestParam(value = "pageNumber") pageNumber: Int
  ): PageResponse<MemberSignInHistory> =
    memberService.readMemberSignInHistory(
      customUserDetails.member.id,
      customUserDetails.member.role,
      startDateTime,
      endDateTime,
      PageRequest.of(pageNumber, pageSize)
    )
}