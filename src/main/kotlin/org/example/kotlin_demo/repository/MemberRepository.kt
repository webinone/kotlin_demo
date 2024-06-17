package org.example.kotlin_demo.repository

import org.example.kotlin_demo.model.entity.MemberEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface MemberRepository : JpaRepository<MemberEntity, Long> {

  fun findByEmail(username: String): MemberEntity?

  fun existsByEmail(email: String): Boolean
}