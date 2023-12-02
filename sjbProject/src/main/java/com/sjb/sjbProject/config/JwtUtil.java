package com.sjb.sjbProject.config;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

public class JwtUtil {

    private static final String SECRET_KEY = "sjb.com&2023";  // JWT密鑰

    // 生成JWT令牌
    public static String generateJWT(String loginId,String email){
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + 3600000*24);  // 有效期1天

        return Jwts.builder()
                .claim("email", email)
                .setSubject(loginId)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();
    }

    // 解析JWT令牌
    public static Claims parseJWT(String token){
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
    }
}
