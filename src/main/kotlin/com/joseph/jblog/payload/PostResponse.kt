package com.joseph.jblog.payload

import lombok.AllArgsConstructor
import lombok.Data
import lombok.NoArgsConstructor

@Data
@AllArgsConstructor
@NoArgsConstructor
class PostResponse {
    var content: MutableList<PostDTO>? = null
    var pageNo: Int? =null
    var pageSize: Int? = null
    var totalElements: Int? = null
    var totalPages: Int? = null
    var last: Boolean = false
}