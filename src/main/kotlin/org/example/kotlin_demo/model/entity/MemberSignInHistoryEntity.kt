package org.example.kotlin_demo.model.entity

import jakarta.persistence.*

@Entity
@Table(name = "tb_member_signin_history")
class MemberSignInHistoryEntity(

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private val id: Long? = null,

  @Column(name = "email", nullable = false)
  private var email: String,

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "member_id")
  var memberEntity: MemberEntity

) : BaseEntity() {

  companion object {
    fun create(email: String, memberEntity: MemberEntity): MemberSignInHistoryEntity =
      MemberSignInHistoryEntity(
        email = email,
        memberEntity = memberEntity
      )
  }
}
