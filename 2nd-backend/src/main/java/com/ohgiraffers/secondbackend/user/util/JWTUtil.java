package com.ohgiraffers.secondbackend.user.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Component
public class JWTUtil {

    private final Key key;

    public JWTUtil(@Value("${jwt.secret}") String secret) {
        this.key= Keys.hmacShaKeyFor(secret.getBytes());
    }

    //토큰생성
    private String createToken(String usernameme,String role, long expiretime){
        Claims claims= Jwts.claims();
        claims.put("username",usernameme);
        claims.put("role",role);

        Date now=new Date();
        Date validity=new Date(now.getTime()+expiretime);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    //Access Token 만들기 15분 정도?
    private String createAccessToken(String username, String role){
        return createToken(username,role, TimeUnit.MINUTES.toMillis(15));
    }

    //Refresh Token 만들기
    private String createRefreshToken(String username, String role){
        return createToken(username,role, TimeUnit.DAYS.toMillis(1));
    }

    //만료시간 추출?
    public long getExpirationTime(String token){
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getExpiration()
                .getTime();
    }

    public String getUsername(String token){
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("username",String.class);
    }

    public String getRole(String token){
        String role=Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("role",String.class);
        return "ROLE_"+role;
    }

    public boolean isTokenExpired(String token){
        try{
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .getExpiration()
                    .before(new Date());
        }catch(ExpiredJwtException e){
           //만료되면 트루!
            return true;
        }catch(Exception e){
            //유효하지 않으면
            return true;
        }
    }


    public Boolean validateToken(String token){
        try{
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
            return true;
        }catch(io.jsonwebtoken.security.SecurityException | MalformedJwtException){

        }catch(UnsupportedJwtException e){

        }catch(IllegalArgumentException e){

        }
        return false;
    }


}
