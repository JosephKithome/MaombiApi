package com.joseph.jblog.controller

import com.joseph.jblog.payload.CommentDTO
import com.joseph.jblog.payload.PostDTO
import com.joseph.jblog.service.CommentService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/api")
class CommentController {
    @Autowired lateinit var commentService: CommentService

    @PostMapping("/posts/{postId}/comments")
    fun createComment(@PathVariable("postId")postId: Long,  @RequestBody commentDTO: CommentDTO):ResponseEntity<CommentDTO>{
        return ResponseEntity(commentService.createComment(postId,commentDTO),HttpStatus.CREATED)
    }

    @GetMapping("/posts/{postId}/comments")
    fun listCommentsByPostId( @PathVariable ("postId") postId: Long): ResponseEntity<MutableList<CommentDTO>>{
        return ResponseEntity(commentService.getCommentsPostById(postId), HttpStatus.OK)

    }

    @GetMapping("/posts/{postId}/comments/{commentId}")
    fun getCommentById(@PathVariable("postId") postId: Long, @PathVariable("commentId")commentId: Long): ResponseEntity<CommentDTO> {
        return ResponseEntity(commentService.getCommentById(postId, commentId), HttpStatus.OK)
    }

    @PutMapping("/posts/{postId}/comments/{commentId}")
    fun updateComment(@PathVariable(value = "postId") postId: Long,
                      @PathVariable(value = "commentId") commentId: Long,
                      @RequestBody commentDTO: CommentDTO): ResponseEntity<CommentDTO> {
        return ResponseEntity(commentService.updateComment(postId, commentId, commentDTO), HttpStatus.OK)
    }
    @DeleteMapping("/posts/{postId}/comments/{commentId}")
    fun deleteComment(@PathVariable("postId") postId: Long, @PathVariable("commentId")commentId: Long): ResponseEntity<String> {
        commentService.deleteComment(postId, commentId)
        return ResponseEntity("Comment deleted successfully", HttpStatus.ACCEPTED)
    }

}