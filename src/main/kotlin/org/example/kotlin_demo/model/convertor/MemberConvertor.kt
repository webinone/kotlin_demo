package org.example.kotlin_demo.model.convertor

import org.example.kotlin_demo.model.domain.Member
import org.example.kotlin_demo.model.entity.MemberEntity

class MemberConvertor {

  companion object {
    fun toDomain(memberEntity: MemberEntity): Member {

      return with(memberEntity) {
        Member.of(
          id = id!!,
          email = email,
          password = password,
          regNo = regNo,
          role = role
        )
      }
    }
  }
}