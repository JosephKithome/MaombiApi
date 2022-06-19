package com.joseph.jblog.security

import com.joseph.jblog.entity.Role
import com.joseph.jblog.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import java.util.stream.Collectors

@Service
class CustomUserDetailsService : UserDetailsService {

    @Autowired lateinit var userRepository: UserRepository

    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(usernameOrEmail: String): UserDetails {
        val user = userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail).orElseThrow {
            UsernameNotFoundException(
                "User not found with username or email:$usernameOrEmail"
            )
        }
        return User(user.email, user.password, mapRolesToAuthorities(user.roles))
    }

    private fun mapRolesToAuthorities(roles: Set<Role>?): Collection<GrantedAuthority?> {
        return roles!!.stream().map { role: Role ->
            SimpleGrantedAuthority(
                role.name
            )
        }.collect(Collectors.toList())
    }
}