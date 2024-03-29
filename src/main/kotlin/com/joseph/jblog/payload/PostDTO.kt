package com.joseph.jblog.payload

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import lombok.Data
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.Size


@ApiModel("Post model")
@Data
class PostDTO {
    @ApiModelProperty(value="Post id")
    var id: Int? = null
    @ApiModelProperty(value="Post title")
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