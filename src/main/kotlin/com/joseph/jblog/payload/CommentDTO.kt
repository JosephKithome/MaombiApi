package com.joseph.jblog.payload

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import lombok.Data


@ApiModel(description = "Post model information")
@Data
class CommentDTO {
    @ApiModelProperty(value ="Comment id")
    var id: Long? = null
    @ApiModelProperty(value="Comment title or name")
    var name: String? = null

    @ApiModelProperty(value="Comment email")
    var email: String? = null

    @ApiModelProperty(value="Comment body")
    var body: String?=null

}