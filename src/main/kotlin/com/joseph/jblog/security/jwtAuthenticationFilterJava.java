package com.joseph.jblog.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class jwtAuthenticationFilterJava extends OncePerRequestFilter {

    //Inject the required dependencies
    @Autowired JwtTokenProvider jwtTokenProvider;
    private CustomUserDetailsService customUserDetailsService;
    @Autowired

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        //
        //Get Jwt token from httpRequest
        //Validate the token
        // get username from the token
        // load user associated with the token
        // set the infor to spring security

        String token = getJwtFromToken(request);
        if (StringUtils.hasText(token) && jwtTokenProvider.validateToken(token)){
            // Get username from token
            String username = jwtTokenProvider.getUsernameFromJwt(token);
            //Load user associated with the token
            UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);

            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());

            //set details here
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            //set spring security context
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
        //call filter method
        filterChain.doFilter(request,response);
    }
    // Bearer <access token>
    private String getJwtFromToken(HttpServletRequest  request){
     String bearerToken = request.getHeader("Authorization");
        //
        // check whether the token has a string or not
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")){
            return bearerToken.substring(7, bearerToken.length());
        }
        return  null;
    }
}
