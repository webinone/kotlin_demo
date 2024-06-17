package org.example.kotlin_demo.common.extension

import org.example.kotlin_demo.model.api.PageResponse
import org.springframework.data.domain.Page

// page 객체 변환
fun <T> Page<T>.toPageResponse(): PageResponse<T> =
  PageResponse(
    content = this.content,
    totalCount = this.totalElements,
    totalPages = this.totalPages,
    currentPage = this.number
  )
