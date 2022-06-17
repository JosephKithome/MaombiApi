package com.joseph.jblog.payload

import lombok.Data
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.Size


@Data
class PostDTO {
    var id: Int? = null

    @NotEmpty
    @Size(min = 2)
    var title: String? =null

    @NotEmpty
    @Size(min = 10)
    var description: String?=null
    @NotEmpty
    var content: String?=null
    var comments: Set<CommentDTO>? = null

}