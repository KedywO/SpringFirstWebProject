package com.hhproject.web.security;


import com.hhproject.web.exceptions.CustomExeption;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.security.*;
import java.security.cert.CertificateException;
import java.text.ParseException;

import static io.jsonwebtoken.Jwts.parser;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class JwtProvider {

    private KeyStore keyStore;


    @PostConstruct
    public void init(){
        try {
            keyStore = KeyStore.getInstance("JKS");
            InputStream resoAsStream = getClass().getResourceAsStream("/springblog.jks");
            keyStore.load(resoAsStream,"secret".toCharArray());

        } catch (KeyStoreException |CertificateException |NoSuchAlgorithmException|IOException  e) {
            throw new CustomExeption("Error when loading keystore");
        }
    }


    public String generateToken(Authentication authentication){
        org.springframework.security.core.userdetails.User principal = (org.springframework.security.core.userdetails.User) authentication.getPrincipal();
        return Jwts.builder()
                .setSubject(principal.getUsername())
                .signWith(getPrivateKey())
                .compact();
    }

    public Claims getClaims(String jwt) throws ParseException {
        Claims claims = Jwts.parserBuilder().setSigningKey(getPublicKey())
                .build().parseClaimsJws(jwt).getBody();
        return claims;
    }

    public String getUsernameFromJwt(String token) throws ParseException {
        return getClaims(token).getSubject();
    }
//    ########################### KEY RETRIVERS #####################
    private PublicKey getPublicKey() {
        try {
            return keyStore.getCertificate("springblog").getPublicKey();
        }catch (KeyStoreException e){
            throw new CustomExeption("Error: Public key");
        }
    }

    private PrivateKey getPrivateKey() {
        try {
            return (PrivateKey) keyStore.getKey("springblog", "secret".toCharArray());

        } catch (KeyStoreException | NoSuchAlgorithmException | UnrecoverableKeyException e){
            throw new CustomExeption("Error when retriving private key");
        }
    }

    public boolean validateToken(String jwt) throws ParseException { //parser().setSigningKey(getPublickey()).parseClaimsJws(jwt);
//        if(!getClaims(jwt).isEmpty()){
//            return true;
//        }
//        return false;
        Jwts.parserBuilder().setSigningKey(getPublicKey()).build().parseClaimsJws(jwt);
        return true;
    }
}
