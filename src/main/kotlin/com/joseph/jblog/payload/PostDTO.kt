package com.joseph.jblog.payload

import lombok.Data


@Data
class PostDTO {
    var id: Int? = null
    var title: String? =null
    var description: String?=null
    var content: String?=null
    var comments: Set<CommentDTO>? = null

}