package com.joseph.jblog.payload

import lombok.Data

@Data
class LoginDTO {
    var usernameOrEmail: String? = null
    var password: String? = null
}