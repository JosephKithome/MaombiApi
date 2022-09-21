package com.springboot.blog.security

import com.joseph.jblog.security.CustomUserDetailsService
import com.joseph.jblog.security.JwtTokenProvider
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.util.StringUtils
import org.springframework.web.filter.OncePerRequestFilter
import java.io.IOException
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class JwtAuthenticationFilter : OncePerRequestFilter() {
    // inject dependencies
    @Autowired
    private val tokenProvider: JwtTokenProvider? = null

    @Autowired
    private val customUserDetailsService: CustomUserDetailsService? = null

    @Throws(ServletException::class, IOException::class)
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        // get JWT (token) from http request
        val token = getJWTfromRequest(request)
        // validate token
        if (StringUtils.hasText(token) && tokenProvider!!.validateToken(token)) {
            // get username from token
            val username: String = tokenProvider.getUsernameFromJWT(token)
            // load user associated with token
            val userDetails: UserDetails = customUserDetailsService!!.loadUserByUsername(username)
            val authenticationToken = UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.authorities
            )
            authenticationToken.details = WebAuthenticationDetailsSource().buildDetails(request)
            // set spring security
            SecurityContextHolder.getContext().authentication = authenticationToken
        }
        filterChain.doFilter(request, response)
    }

    // Bearer <accessToken>
    private fun getJWTfromRequest(request: HttpServletRequest): String? {
        val bearerToken = request.getHeader("Authorization")
        return if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            bearerToken.substring(7, bearerToken.length)
        } else null
    }
}