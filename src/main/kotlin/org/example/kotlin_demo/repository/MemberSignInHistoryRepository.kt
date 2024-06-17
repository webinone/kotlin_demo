package org.example.kotlin_demo.repository

import org.example.kotlin_demo.model.entity.MemberSignInHistoryEntity
import org.example.kotlin_demo.repository.querydsl.MemberSignInHistoryRepositoryCustom
import org.springframework.data.jpa.repository.JpaRepository

interface MemberSignInHistoryRepository : JpaRepository<MemberSignInHistoryEntity, Long>,
  MemberSignInHistoryRepositoryCustom {

}
