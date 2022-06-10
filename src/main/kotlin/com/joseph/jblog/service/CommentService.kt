package com.joseph.jblog.service

import com.joseph.jblog.payload.CommentDTO

interface CommentService {

    fun createComment(postId: Long, req: CommentDTO): CommentDTO
    fun getCommentsPostById(postId: Long): MutableList<CommentDTO>
    fun getCommentById(postId: Long, commentId: Long): CommentDTO
    fun updateComment(postId: Long, commentId: Long, commentDTO: CommentDTO): CommentDTO
    fun deleteComment(postId: Long, commentId: Long)
}