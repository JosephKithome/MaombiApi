package com.joseph.jblog.repository

import com.joseph.jblog.entity.Role
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface RoleRepository: JpaRepository<Role,Long> {

    fun  findByName(name: String): Optional<Role>

}