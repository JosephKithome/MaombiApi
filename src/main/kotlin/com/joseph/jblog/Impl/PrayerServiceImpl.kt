package com.joseph.jblog.Impl

import com.joseph.jblog.entity.Prayer
import com.joseph.jblog.payload.PostDTO
import com.joseph.jblog.payload.PostResponse
import com.joseph.jblog.repository.PrayerRepository
import com.joseph.jblog.service.PrayerService
import com.joseph.jblog.utils.LogHelper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import java.util.*
import java.util.stream.Collectors


@Service
class PrayerServiceImpl : PrayerService {

    @Autowired
    private lateinit var prayerRepository: PrayerRepository

    override fun createPost(postDTo: PostDTO): PostDTO {
        //
        var prayer: Prayer = mapToEntity(postDTo)
        //Commit to the repository
        var postResp = prayerRepository.save(prayer)

        //Return post dto as the response
        var postDTO: PostDTO = mapToDTO(postResp)
        return postDTO
    }

    override fun getAllPosts(pageNo: Int, pageSize: Int, sortBy: String, sortDir: String):PostResponse {

        //Sort
        val sort: Sort = if (sortDir == Sort.Direction.ASC.name) Sort.by(sortBy).ascending() else Sort.by(sortBy).descending()

        val pageable: Pageable = PageRequest.of(pageNo,pageSize,sort)
        val posts = prayerRepository.findAll(pageable)

        //Get content for the page object
        val listOfPrayers: MutableList<Prayer> = posts.content
        val content: MutableList<PostDTO> = listOfPrayers.stream().map { x->mapToDTO(x) }.collect(Collectors.toList())

        val postResponse = PostResponse()
        postResponse.content =content
        postResponse.pageNo = posts.number
        postResponse.pageSize = postResponse.pageSize
        postResponse.totalElements = posts.numberOfElements
        postResponse.totalPages = posts.totalPages
        postResponse.last = posts.isLast
        return postResponse
    }

    override fun getPostById(id: Long): Optional<Prayer> {
        return prayerRepository.findById(id)
    }

    override fun updatePost(id: Long, postDTO: PostDTO): PostDTO {
        val post = prayerRepository.findById(id)

        if (post.isPresent){
            try {
              val post = post.get()
                post.title = postDTO.title
                post.description = postDTO.description
                post.content = postDTO.content
                prayerRepository.save(post)

                //Return post dto as the response
                var postDTO: PostDTO = mapToDTO(post)

            }catch (e: Exception){
              LogHelper.debug(e.message.toString())
            }
        }else{
            throw Exception("No record found with id $id")
        }
        return postDTO
    }

    override fun deletePost(id: Long) {
        val post = prayerRepository.findById(id)

        if (post.isPresent){
            return prayerRepository.deleteById(id)
        }else{
            throw  Exception("Post has been deleted or post with that id does not exist")
        }
    }

    private fun mapToDTO(prayerResp: Prayer): PostDTO {
        var postDTo: PostDTO = PostDTO()
        postDTo.title = prayerResp.title
        postDTo.description = prayerResp.description
        postDTo.content = prayerResp.content
        postDTo.id = prayerResp.id!!.toInt()
        return postDTo
    }

    private fun mapToEntity( postDTo: PostDTO): Prayer {
        //Convert DTO to entity
        var prayer: Prayer = Prayer()
        prayer.title = postDTo.title
        prayer.description = postDTo.description
        prayer.content = postDTo.content
        return prayer
    }
}