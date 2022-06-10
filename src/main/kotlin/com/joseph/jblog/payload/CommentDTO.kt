package com.joseph.jblog.payload

import lombok.Data

@Data
class CommentDTO {
    var id: Long? = null
    var name: String? = null
    var email: String? = null
    var body: String?=null

}