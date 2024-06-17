package org.example.kotlin_demo.common.utils

import com.querydsl.core.types.EntityPath
import com.querydsl.core.types.Order
import com.querydsl.core.types.OrderSpecifier
import com.querydsl.core.types.dsl.*
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.util.CollectionUtils
import org.springframework.util.StringUtils
import java.lang.reflect.Field
import java.time.Instant
import java.time.LocalDateTime
import java.util.*
import java.util.stream.Collectors

class QuerydslUtils {

  companion object {
    fun getEndOffset(fetch: List<*>, pageable: Pageable): Int {
      val start = pageable.offset.toInt()

      return if ((start + pageable.pageSize) > fetch.size) fetch.size else (start + pageable.pageSize)
    }

    fun eq(field: StringPath, value: String): BooleanExpression? {
      return if (StringUtils.hasText(value)) field.eq(value) else null
    }

    fun eq(field: NumberPath<Long?>, value: Long?): BooleanExpression? =
      value?.let { field.eq(it) }

    fun eq(field: BooleanPath, value: Boolean?): BooleanExpression? {
      return if (value != null) field.eq(value) else null
    }

    fun `in`(field: StringPath, value: List<String?>): BooleanExpression? {
      return if (!CollectionUtils.isEmpty(value)) field.`in`(value) else null
    }

    inline fun <reified E : Enum<E>> eq(field: EnumPath<E>, value: Any?): BooleanExpression? {
      return if (value != null && value is E) field.eq(value) else null
    }

    fun goe(field: StringPath, value: String?): BooleanExpression? {
      return if (value != null) field.goe(value) else null
    }

    fun loe(field: StringPath, value: String?): BooleanExpression? {
      return if (value != null) field.loe(value) else null
    }

    fun goe(field: DateTimePath<LocalDateTime?>, value: LocalDateTime?): BooleanExpression? {
      return if (value != null) field.goe(value) else null
    }

    fun loe(field: DateTimePath<LocalDateTime?>, value: LocalDateTime?): BooleanExpression? {
      return if (value != null) field.loe(value) else null
    }

    fun barogoDate(field: DateTimePath<Instant?>?): StringTemplate {
      return Expressions.stringTemplate("to_char({0} + interval '2 hour', 'yyyymmdd')", field)
    }


    fun like(field: StringPath, value: String?): BooleanExpression? {
      return if (StringUtils.hasText(value)) field.contains(value) else null
    }
  }


}