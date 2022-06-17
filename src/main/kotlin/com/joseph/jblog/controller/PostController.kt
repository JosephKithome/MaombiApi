package com.joseph.jblog.controller

import com.joseph.jblog.entity.Prayer
import com.joseph.jblog.payload.PostDTO
import com.joseph.jblog.payload.PostResponse
import com.joseph.jblog.service.PrayerService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.*
import javax.validation.Valid


@RestController
@RequestMapping("/api/posts")
class PostController {

    @Autowired
    private lateinit var  prayerService: PrayerService

    //Instance of AppConstants class

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    fun createPost(@Valid @RequestBody postDTO: PostDTO):  ResponseEntity<PostDTO>{
        return ResponseEntity(prayerService.createPost(postDTO), HttpStatus.CREATED)
    }

    //
    //Retrieve all the posts
    @GetMapping
    fun listAllPosts(
        @RequestParam(value = "pageNo", defaultValue = "0", required = false) pageNo: Int,
        @RequestParam(value = "pageSize", defaultValue = "10", required = false) pageSize: Int,
        @RequestParam(value = "sortBy", defaultValue = "id", required = false)sortBy: String,
        @RequestParam(value = "sortDir", defaultValue = "asc", required = false)sortDir: String
    ): ResponseEntity<PostResponse>{
        return ResponseEntity(prayerService.getAllPosts(pageNo,pageSize, sortBy,sortDir), HttpStatus.OK)
    }
    //
    //Get post by id
    @GetMapping("/{id}")
    fun getPostById(@PathVariable("id") id: Long): ResponseEntity<Prayer>{
        return  ResponseEntity(prayerService.getPostById(id), HttpStatus.OK)
    }
    //
    //Update a single post
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    fun updatePost(@PathVariable("id")id: Long, @RequestBody postDTO: PostDTO): ResponseEntity<PostDTO>{
        return ResponseEntity(prayerService.updatePost(id,postDTO), HttpStatus.OK)
    }
    //
    //Delete Post Api detail
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    fun deletePost(@PathVariable("id") id: Long):ResponseEntity<String>{
        prayerService.deletePost(id)
        return ResponseEntity("Post deleted successfully", HttpStatus.ACCEPTED)
    }

}