package com.joseph.jblog.utils

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder

object PasswordEncoderGenerator {
    @JvmStatic
    fun main(args: Array<String>) {
        val passwordEncoder: PasswordEncoder = BCryptPasswordEncoder()
        println(passwordEncoder.encode("password"))
    }
}