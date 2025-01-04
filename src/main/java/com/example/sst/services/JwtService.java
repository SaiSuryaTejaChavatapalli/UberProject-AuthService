package com.example.sst.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService implements CommandLineRunner {

    // We access it from application.properties
    @Value("${jwt.expiry}")
    private int expiry;

    @Value("${jwt.secret}")
    private  String SECRET;

    public Key getKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    // This method creates a brand-new JWT token based on payload
    public String createToken(Map<String, Object> payload, String email){

        Date now=new Date();

        Date expiryDate=new Date(now.getTime()+expiry*1000L);

        return Jwts.builder()
                .setClaims(payload)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(expiryDate)
                .setSubject(email)
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
    }


    public String createToken(String email){
        return createToken(new HashMap<>(),email);
    }

    public Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
    public <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
        final Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);
    }


    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
    public String extractEmail(String token) {
        // extract the username from jwt token
        return extractClaim(token, Claims::getSubject);
    }

    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }


    public Boolean validateToken(String token, String email){
        final String userEmailFetchedFromToken=extractEmail(token);
        return (userEmailFetchedFromToken.equals(email) && !isTokenExpired(token));
    }

    public Object extractPayload(String token, String payloadKey){
       Claims claims=extractAllClaims(token);
       return claims.get(payloadKey);
    }

    @Override
    public void run(String... args) throws Exception {
        Map<String,Object> mp=new HashMap<>();
        mp.put("email","a@b.com");
        mp.put("phoneNumber","9999999");
        String result=createToken(mp,"saisuryateja");
        System.out.println("Generated token is:"+result);

        System.out.println(extractPayload(result,"phoneNumber"));
        System.out.println(extractPayload(result,"email "));
    }
}
