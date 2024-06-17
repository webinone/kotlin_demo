package org.example.kotlin_demo.model.entity

import jakarta.persistence.*
import jakarta.persistence.Id
import org.example.kotlin_demo.model.enums.RoleType
import jakarta.persistence.Entity

@Table(name = "tb_member")
@Entity
class MemberEntity(

  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  val id: Long? = null,
  @Column(name = "email", nullable = false, unique = true)
  var email: String,
  @Column(name = "password", nullable = false)
  var password: String,
  @Column(name = "reg_no", nullable = false)
  var regNo: String,

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  var role: RoleType,

  ) : BaseEntity() {

  fun updateEmailAndPasswordAndRegNo(email: String?, password: String?, regNo: String?) {
    email?.let { this.email = it }
    password?.let { this.password = it }
    regNo?.let { this.regNo = it }
  }

  companion object {
    fun create(email: String, password: String, regNo: String, role: RoleType): MemberEntity {
      return MemberEntity(
        email = email,
        password = password,
        regNo = regNo,
        role = role
      )
    }
  }
}