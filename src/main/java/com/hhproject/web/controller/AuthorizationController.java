package com.hhproject.web.controller;


import com.hhproject.web.dto.LoginRequest;
import com.hhproject.web.dto.RegisterRequest;
import com.hhproject.web.dto.TokenResponse;
import com.hhproject.web.service.AuthorizationService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@RestController
@RequestMapping(path = "/auth")
@AllArgsConstructor
public class AuthorizationController {

    final private AuthorizationService authService;


    @PostMapping("/signup")
    private ResponseEntity<String> signup(@RequestBody RegisterRequest registerRequest){
        authService.signup(registerRequest);
        return new ResponseEntity<>("User registration successful!", HttpStatus.CREATED);
    }

    @GetMapping("/accountVerification/{token}")
    private ResponseEntity<String> verifiyAcc (@PathVariable String token){
        authService.verifyAcc(token);
        return new ResponseEntity<>("Account Verified!", HttpStatus.OK);
    }

    @PostMapping("/login")
    public TokenResponse login(@RequestBody LoginRequest loginRequest) throws ParseException {
        return authService.login(loginRequest);

    }


}
