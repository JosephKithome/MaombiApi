package com.joseph.jblog.payload

import lombok.Data

@Data
class SignUpDTO {
    val name: String? = null
    val username: String? = null
    val email: String? = null
    val password: String? = null

}