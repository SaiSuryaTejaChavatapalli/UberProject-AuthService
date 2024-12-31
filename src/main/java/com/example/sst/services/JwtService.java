package com.example.sst.services;

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

@Service
public class JwtService implements CommandLineRunner {

    // We access it from application.properties
    @Value("${jwt.expiry}")
    private int expiry;

    @Value("${jwt.secret}")
    private  String SECRET;

    private Key getKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    // This method creates a brand-new JWT token based on payload
    private String createToken(Map<String, Object> payload, String username){

        Date now=new Date();

        Date expiryDate=new Date(now.getTime()+expiry*1000L);

        return Jwts.builder()
                .setClaims(payload)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(expiryDate)
                .setSubject(username)
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    @Override
    public void run(String... args) throws Exception {
        Map<String,Object> mp=new HashMap<>();
        mp.put("email","a@b.com");
        mp.put("phoneNumber","9999999");
        String result=createToken(mp,"saisuryateja");
        System.out.println("Generated token is:"+result);
    }
}
