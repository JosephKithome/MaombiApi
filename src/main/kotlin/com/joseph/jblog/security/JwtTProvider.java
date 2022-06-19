package com.joseph.jblog.security;

import com.joseph.jblog.exception.PrayerAPIException;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTProvider {

    @Value("${app.jwt-secret}")
    private String jwt_secret;

    @Value("${app.jwt-expiration-milliseconds}")
    private String jwt_exp_date;

    // generate token
    public String  generateToken(Authentication authentication){
        String username = authentication.getName();
        Date currentDate = new Date();
        Date  expireDate = new Date(currentDate.getTime() + jwt_exp_date);

        String token = Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.ES512,jwt_secret)
                .compact();

        return token;
    }

    // get username from the token
    public String getUsernameFromJwt(String token){
        Claims claims = Jwts.parser()
                .setSigningKey(jwt_secret)
                .parseClaimsJws(token)
                .getBody();
        return  claims.getSubject();
    }

    //validate jwt token
    public boolean validateToken(String token){
        try{
            Jwts.parser().setSigningKey(jwt_secret).parseClaimsJws(token);
            return  true;
        }catch (MalformedJwtException exception){
            throw  new PrayerAPIException(HttpStatus.BAD_REQUEST,"Invalid Jwt token");
        }catch (ExpiredJwtException exception){
            throw new PrayerAPIException(HttpStatus.BAD_REQUEST, "Expired Jwt token");
        }
        catch(IllegalArgumentException illegalArgumentException){
            throw  new PrayerAPIException(HttpStatus.BAD_REQUEST, "Jwt class string is empty");
        }
        catch (SignatureException signatureException){
            throw new PrayerAPIException(HttpStatus.BAD_REQUEST,"Invalid Jwt signature");
        }
    }
}
