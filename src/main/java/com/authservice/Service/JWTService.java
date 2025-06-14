package com.authservice.Service;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JWTService {

    private static final String KEY = "my-key";
    private static final long TIME =86400000;


    public String generateToken(String username, String role){

        return  JWT.create()
                .withSubject(username)
                .withClaim("role", role)
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis()+TIME))
                .sign(Algorithm.HMAC256(KEY));
    }


    public String validateJwtandRetriveHeader(String token){
        return JWT.require(Algorithm.HMAC256(KEY))
                .build()
                .verify(token)
                .getSubject();
    }

}
