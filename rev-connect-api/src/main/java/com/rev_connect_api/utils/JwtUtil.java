package com.rev_connect_api.utils;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;


import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Map;

public class JwtUtil {

    private static String secret = "fujbgbgibuaeigbgbeigbiebgiebgrgr"; // Key



    // generate Token
    public static String generateToken(Map<String, Object> claims) {
        //Token expire in 1 hour
        return createJwt(secret,1000 * 60 * 60 * 1,claims);
    }
    /**
     * generate jwt
     * Use Hs256 algorithm, private key uses fixed key
     *
     * @param secretKey jwt key
     * @param ttlMillis jwt expire time
     * @param claims    Setting claims
     * @return
     */
    public static String createJwt(String secretKey, long ttlMillis, Map<String, Object> claims) {
        // header
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        // JWT time
        long expMillis = System.currentTimeMillis() + ttlMillis;
        Date exp = new Date(expMillis);

        //Generates an HMAC key, selecting the appropriate HMAC algorithm based on the length of the supplied byte array, and returns a corresponding SecretKey object.
        SecretKey key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));

        // jwt body
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(exp)
                .signWith(key, signatureAlgorithm)
                .compact();

    }

    /**
     * Token Decryption
     *
     * @param secretKey JWT secret key This secret key must be kept on the server side and cannot be exposed,
     *                  otherwise the sign can be forged.
     *                  If connecting to multiple clients, it is recommended to transform it into multiple
     * @param token     Encrypted token
     * @return
     */
    /**
     * Token Decryption
     *
     * @param secretKey JWT secret key This secret key must be kept on the server side and cannot be exposed,
     *                  otherwise the sign can be forged.
     *                  If connecting to multiple clients, it is recommended to transform it into multiple
     * @param token     Encrypted token
     * @return
     */
    public static Claims parseJWT(String secretKey, String token) {
        SecretKey key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));

        // Parse the token
        JwtParser jwtParser = Jwts.parserBuilder()
                .setSigningKey(key)
                .build();

        return jwtParser.parseClaimsJws(token).getBody();
    }



}