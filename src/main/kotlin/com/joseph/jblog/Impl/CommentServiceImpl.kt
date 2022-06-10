package com.joseph.jblog.Impl

import com.joseph.jblog.entity.Comment
import com.joseph.jblog.exception.PrayerAPIException
import com.joseph.jblog.payload.CommentDTO
import com.joseph.jblog.repository.CommentRepository
import com.joseph.jblog.repository.PrayerRepository
import com.joseph.jblog.service.CommentService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import java.util.stream.Collectors

@Service
class CommentServiceImpl: CommentService {
    @Autowired lateinit var commentRepository: CommentRepository

    @Autowired lateinit var  prayerRepository: PrayerRepository

    override fun createComment(postId: Long, req: CommentDTO): CommentDTO {
        val comment: Comment = mapToEntity(req)
           //
           //Retrieve post entity for which to create a comment for here
           val post = prayerRepository.findById(postId).orElseThrow()

           //set post to comment entity
           comment.prayer =post
           //
           //save the comment entity
           val commen: Comment =commentRepository.save(comment)
           //Return a dto
        return mapToDTo(commen)
    }

    override fun getCommentsPostById(postId : Long): MutableList<CommentDTO> {
        //
        //retrieve list of comments by postId
        val comment: MutableList<Comment> = commentRepository.findCommentsByPrayerId(postId)
        print("We got ${comment.size}")
        val response = comment.stream().map { comment-> mapToDTo(comment) }.collect(Collectors.toList())
        /*

        Map the result to dto
        */
        return response

    }

    override fun getCommentById(postId: Long, commentId: Long): CommentDTO {
        //
        //Get post from post repository
        val post = prayerRepository.findById(postId).get()
        //
        //Get comment by comment Id
        val comment = commentRepository.findById(commentId)

        if (comment.get().prayer!!.id !=post.id){
            throw  PrayerAPIException(HttpStatus.BAD_REQUEST,"Comment does not belong to a post")
        }

        return mapToDTo(comment.get())
    }

    override fun updateComment(postId: Long, commentId: Long, req: CommentDTO): CommentDTO {
        //
        //Get post from post repository
        val post = prayerRepository.findById(postId).get()
        //
        //Get comment by comment Id
        val comment: Comment = commentRepository.findById(commentId).get()

        if (comment.prayer!!.id !=post.id){
            throw  PrayerAPIException(HttpStatus.BAD_REQUEST,"Comment does not belong to a post")
        }
        comment.name = req.name
        comment.email = req.email
        comment.body = req.body

        val updatedComment = commentRepository.save(comment)
        return mapToDTo(updatedComment)
    }

    override fun deleteComment(postId: Long, commentId: Long) {
        //
        //Get post from post repository
        val post = prayerRepository.findById(postId).get()
        //
        //Get comment by comment Id
        val comment: Comment = commentRepository.findById(commentId).get()
        if (comment.prayer!!.id !=post.id){
            throw  PrayerAPIException(HttpStatus.BAD_REQUEST,"Comment does not belong to a post")
        }
        //
        //delete the comment
        return commentRepository.deleteById(commentId)

    }

    //convert an entity into a dto
    fun mapToDTo(comment: Comment): CommentDTO{
       val commentDTO = CommentDTO()
       commentDTO.id = comment.id
       commentDTO.name = comment.name
       commentDTO.email = comment.email
       commentDTO.body = comment.body
        return commentDTO
    }

    //
    //Convert dto into an entity
    // Note that: you can create a constructor in the entity class
    //to serve this purpose
    fun mapToEntity(commentDTO: CommentDTO): Comment{
        val comment = Comment()
        comment.id = commentDTO.id
        comment.name = commentDTO.name
        comment.email = commentDTO.email
        comment.body = commentDTO.body

        return  comment
    }
}