package org.example.kotlin_demo.model.entity

import jakarta.persistence.Column
import jakarta.persistence.EntityListeners
import jakarta.persistence.MappedSuperclass
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime

@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
abstract class BaseEntity(
) {
  @CreatedDate
  @Column(name = "created_at", nullable = false, updatable = false)
  protected var createdAt: LocalDateTime = LocalDateTime.now()

  @LastModifiedDate
  @Column(name = "updated_at", nullable = false)
  protected var updatedAt: LocalDateTime = LocalDateTime.now()
}