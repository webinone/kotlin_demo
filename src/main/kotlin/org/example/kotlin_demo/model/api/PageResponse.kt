package org.example.kotlin_demo.model.api

data class PageResponse<T>(
  val content: List<T>,
  val totalCount: Long,
  val totalPages: Int,
  val currentPage: Int,
)
