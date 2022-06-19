package com.joseph.jblog.repository

import com.joseph.jblog.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface UserRepository: JpaRepository<User,Long> {

    fun findByEmail(email: String): Optional<User>
    fun findByUsername(username: String): Optional<User>
    fun findByUsernameOrEmail(username: String?, email: String?): Optional<User>

    //Checkers
    fun existsByUsername(username: String): Boolean
    fun existsByEmail(email: String): Boolean
}