package com.joseph.jblog.service

import com.joseph.jblog.entity.Prayer
import com.joseph.jblog.payload.PostDTO
import com.joseph.jblog.payload.PostResponse
import java.util.*

interface PrayerService {


    fun createPost(postDTo: PostDTO): PostDTO;
    fun getAllPosts(pageNo: Int, pageSize: Int, sortBy: String, sortDir: String): PostResponse
    fun getPostById(id: Long): Optional<Prayer>
    fun updatePost(id: Long, postDTO: PostDTO): PostDTO
    fun deletePost(id: Long)
}