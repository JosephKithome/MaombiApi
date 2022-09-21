package com.joseph.jblog.controller

import com.joseph.jblog.entity.Prayer
import com.joseph.jblog.payload.PostDTO
import com.joseph.jblog.payload.PostDTOV2
import com.joseph.jblog.payload.PostResponse
import com.joseph.jblog.service.PrayerService
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
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


@Api("API to perform crud operations to our blog api")
@RestController
class PostController {

    @Autowired
    private lateinit var  prayerService: PrayerService

    //Instance of AppConstants class
    @ApiOperation("Create post rest API")
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    fun createPost(@Valid @RequestBody postDTO: PostDTO):  ResponseEntity<PostDTO>{
        return ResponseEntity(prayerService.createPost(postDTO), HttpStatus.CREATED)
    }

    //
    //Retrieve all the posts
    @ApiOperation("Get all posts rest API")
    @GetMapping("/api/v1/posts")
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
    //
    @ApiOperation("Get post by id rest API")
    @GetMapping("/api/v1/posts/{id}")
    fun getPostByIdv1(@PathVariable("id") id: Long): ResponseEntity<Prayer>{
        return  ResponseEntity(prayerService.getPostById(id), HttpStatus.OK)
    }

    // versioning using uri path]
    @ApiOperation("Get post by id v2 rest API")
    @GetMapping("/api/v2/posts/{id}")
    fun getPostByIdv2(@PathVariable("id") id: Long): ResponseEntity<PostDTOV2>{
        val postDTO = prayerService.getPostById(id);
        val postDTOV2 = PostDTOV2();

        postDTOV2.id = postDTO.id
        postDTOV2.content = postDTO.content
        postDTOV2.description = postDTOV2.description
        postDTOV2.title = postDTO.title
//        postDTOV2.comments = postDTO.comments

        val tags : MutableList<String> = mutableListOf()
        tags.add("Java");
        tags.add("Spring Boot")
        tags.add("AWS");

        postDTOV2.tags = tags
        return  ResponseEntity(postDTOV2, HttpStatus.OK)
    }

    // versioning using query params
    @GetMapping("/api/posts/{id}" , params = ["version=3"])
    fun getPostByIdQueryParams(@PathVariable("id") id: Long): ResponseEntity<PostDTOV2>{
        val postDTO = prayerService.getPostById(id);
        val postDTOV2 = PostDTOV2();

        postDTOV2.id = postDTO.id
        postDTOV2.content = postDTO.content
        postDTOV2.description = postDTOV2.description
        postDTOV2.title = postDTO.title
//        postDTOV2.comments = postDTO.comments

        val tags : MutableList<String> = mutableListOf()
        tags.add("Java");
        tags.add("Spring Boot")
        tags.add("AWS");

        postDTOV2.tags = tags
        return  ResponseEntity(postDTOV2, HttpStatus.OK)
    }

    // versioning using custom headers
    @GetMapping("/api/posts/{id}" , headers = ["X-API-VERSION=4"])
    fun getPostByIdCustomHeaders(@PathVariable("id") id: Long): ResponseEntity<PostDTOV2>{
        val postDTO = prayerService.getPostById(id);
        val postDTOV2 = PostDTOV2();

        postDTOV2.id = postDTO.id
        postDTOV2.content = postDTO.content
        postDTOV2.description = postDTOV2.description
        postDTOV2.title = postDTO.title
//        postDTOV2.comments = postDTO.comments

        val tags : MutableList<String> = mutableListOf()
        tags.add("Java");
        tags.add("Spring Boot")
        tags.add("AWS");

        postDTOV2.tags = tags
        return  ResponseEntity(postDTOV2, HttpStatus.OK)
    }
    @GetMapping("/api/posts/{id}" , produces = ["application/vnd.javaguides.v1+json"])
    fun getPostByIdContentNegotiation(@PathVariable("id") id: Long): ResponseEntity<PostDTOV2>{
        val postDTO = prayerService.getPostById(id);
        val postDTOV2 = PostDTOV2();

        postDTOV2.id = postDTO.id
        postDTOV2.content = postDTO.content
        postDTOV2.description = postDTOV2.description
        postDTOV2.title = postDTO.title
//        postDTOV2.comments = postDTO.comments

        val tags : MutableList<String> = mutableListOf()
        tags.add("Java");
        tags.add("Spring Boot")
        tags.add("AWS");

        postDTOV2.tags = tags
        return  ResponseEntity(postDTOV2, HttpStatus.OK)
    }

    // versioning using content negotiation

    //Update a single post
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/api/v1/posts/{id}")
    fun updatePost(@PathVariable("id")id: Long, @RequestBody postDTO: PostDTO): ResponseEntity<PostDTO>{
        return ResponseEntity(prayerService.updatePost(id,postDTO), HttpStatus.OK)
    }
    //
    //Delete Post Api detail
    @ApiOperation("Delete post rest API")
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/api/v1/posts/{id}")
    fun deletePost(@PathVariable("id") id: Long):ResponseEntity<String>{
        prayerService.deletePost(id)
        return ResponseEntity("Post deleted successfully", HttpStatus.ACCEPTED)
    }

}