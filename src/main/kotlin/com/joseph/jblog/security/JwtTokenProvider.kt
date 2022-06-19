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
    private val jwt_secret: String? = null

    @Value("\${app.jwt-expiration-milliseconds}")
    private val jwt_exp_date: String? = null

    // generate token
    fun generateToken(authentication: Authentication): String {
        val username = authentication.name
        val currentDate = Date()
        val expireDate = Date(currentDate.time.toString() + jwt_exp_date)
        return Jwts.builder()
            .setSubject(username)
            .setIssuedAt(Date())
            .setExpiration(expireDate)
            .signWith(SignatureAlgorithm.ES512, jwt_secret)
            .compact()
    }

    // get username from the token
    fun getUsernameFromJwt(token: String?): String {
        val claims = Jwts.parser()
            .setSigningKey(jwt_secret)
            .parseClaimsJws(token)
            .body
        return claims.subject
    }

    //validate jwt token
    fun validateToken(token: String?): Boolean {
        return try {
            Jwts.parser().setSigningKey(jwt_secret).parseClaimsJws(token)
            true
        } catch (exception: MalformedJwtException) {
            throw PrayerAPIException(HttpStatus.BAD_REQUEST, "Invalid Jwt token")
        } catch (exception: ExpiredJwtException) {
            throw PrayerAPIException(HttpStatus.BAD_REQUEST, "Expired Jwt token")
        } catch (illegalArgumentException: IllegalArgumentException) {
            throw PrayerAPIException(HttpStatus.BAD_REQUEST, "Jwt class string is empty")
        } catch (signatureException: SignatureException) {
            throw PrayerAPIException(HttpStatus.BAD_REQUEST, "Invalid Jwt signature")
        }
    }
}