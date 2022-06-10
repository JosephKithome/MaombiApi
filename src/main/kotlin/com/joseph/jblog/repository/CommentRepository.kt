package com.joseph.jblog.repository

import com.joseph.jblog.entity.Comment
import org.springframework.data.jpa.repository.JpaRepository

interface CommentRepository: JpaRepository<Comment, Long> {

    fun findCommentsByPrayerId(long: Long): MutableList<Comment>
}