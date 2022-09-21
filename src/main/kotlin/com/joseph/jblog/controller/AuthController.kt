package com.joseph.jblog.controller

import com.joseph.jblog.entity.Role
import com.joseph.jblog.entity.User
import com.joseph.jblog.payload.JwtAuthResponse
import com.joseph.jblog.payload.LoginDTO
import com.joseph.jblog.payload.SignUpDTO
import com.joseph.jblog.repository.RoleRepository
import com.joseph.jblog.repository.UserRepository
import com.joseph.jblog.security.JwtTokenProvider
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*


@Api(value = "Auth controller exposes login and register")
@RestController
@RequestMapping("/api/auth")
class AuthController {

    @Autowired
    lateinit var  authenticationManager: AuthenticationManager
    @Autowired
    lateinit var  userRepository: UserRepository

    @Autowired
    lateinit var  roleRepository: RoleRepository

    @Autowired
    lateinit var passwordEncoder: PasswordEncoder

    // Inject Jwt Token provider
    @Autowired lateinit var  tokenProvider: JwtTokenProvider

    @ApiOperation(value = "Rest Api to login or signin user to prayer application")
    @PostMapping("/login")
    fun authenticateUser(@RequestBody loginDTO: LoginDTO):ResponseEntity<JwtAuthResponse>{
      val authentication: Authentication =  authenticationManager.authenticate(UsernamePasswordAuthenticationToken(loginDTO.usernameOrEmail,loginDTO.password))

        //Set context to the logged in user
        SecurityContextHolder.getContext().authentication = authentication

        //get token from tokenProvider
        val token: String = tokenProvider.generateToken(authentication)

        return ResponseEntity.ok(JwtAuthResponse(token))
    }

    @ApiOperation(value = "Rest Api to register or signup user to prayer application")
    @PostMapping("/register")
    fun registerUser(@RequestBody signUpDTO: SignUpDTO): ResponseEntity<String> {
        // add a check for username exists

        if (userRepository.existsByUsername(signUpDTO.username!!)){
            return ResponseEntity("Username is already taken", HttpStatus.BAD_REQUEST)
        }
        if (userRepository.existsByEmail(signUpDTO.email!!)){
            return ResponseEntity("Email is already taken!", HttpStatus.BAD_REQUEST)

        }

        //create user object
          val user = User()
        user.name = signUpDTO.name
        user.email = signUpDTO.email
        user.username = signUpDTO.username
        user.password = passwordEncoder.encode(signUpDTO.password)

        //retrieve roles
        val role: Role = roleRepository.findByName("ROLE_ADMIN").get()
        user.roles =(Collections.singleton(role))
//        user.roles!!.stream().map { role.id }
        userRepository.save(user)

        return ResponseEntity("user successfully registered", HttpStatus.OK)

    }
}