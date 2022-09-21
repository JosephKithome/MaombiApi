package com.joseph.jblog.payload

import lombok.Data

@Data
class JwtAuthResponse {
    var accessToken: String? = null
    val tokenType: String = "Bearer"

    constructor(accessToken: String?){
        this.accessToken = accessToken
    }

}