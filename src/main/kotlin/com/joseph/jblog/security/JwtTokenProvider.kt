package com.joseph.jblog.security

import com.joseph.jblog.exception.PrayerAPIException
import io.jsonwebtoken.*
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component
import java.util.*

@Component
class JwtTokenProvider {
    @Value("\${app.jwt-secret}")
    private val jwtSecret: String? = null

    @Value("\${app.jwt-expiration-milliseconds}")
    private val jwtExpirationInMs = 0

    // generate token
    fun generateToken(authentication: Authentication): String {
        val username = authentication.name
        val currentDate = Date()
        val expireDate = Date(currentDate.time + jwtExpirationInMs)
        return Jwts.builder()
            .setSubject(username)
            .setIssuedAt(Date())
            .setExpiration(expireDate)
            .signWith(SignatureAlgorithm.HS512, jwtSecret)
            .compact()
    }

    // get username from the token
    fun getUsernameFromJWT(token: String?): String {
        val claims = Jwts.parser()
            .setSigningKey(jwtSecret)
            .parseClaimsJws(token)
            .body
        return claims.subject
    }

    // validate JWT token
    fun validateToken(token: String?): Boolean {
        return try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token)
            true
        } catch (ex: SignatureException) {
            throw PrayerAPIException(HttpStatus.BAD_REQUEST, "Invalid JWT signature")
        } catch (ex: MalformedJwtException) {
            throw PrayerAPIException(HttpStatus.BAD_REQUEST, "Invalid JWT token")
        } catch (ex: ExpiredJwtException) {
            throw PrayerAPIException(HttpStatus.BAD_REQUEST, "Expired JWT token")
        } catch (ex: UnsupportedJwtException) {
            throw PrayerAPIException(HttpStatus.BAD_REQUEST, "Unsupported JWT token")
        } catch (ex: IllegalArgumentException) {
            throw PrayerAPIException(HttpStatus.BAD_REQUEST, "JWT claims string is empty.")
        }
    }
}