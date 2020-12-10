package com.hhproject.web.service;


import com.hhproject.web.dto.LoginRequest;
import com.hhproject.web.dto.RegisterRequest;
import com.hhproject.web.dto.TokenResponse;
import com.hhproject.web.exceptions.CustomExeption;
import com.hhproject.web.model.EmailBody;
import com.hhproject.web.model.User;
import com.hhproject.web.model.VerificationToken;
import com.hhproject.web.respository.UserRepository;
import com.hhproject.web.respository.VerificationTokenRepository;
import com.hhproject.web.security.JwtProvider;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
@Slf4j
public class AuthorizationService {

    final private PasswordEncoder passwordEncoder;
    final private UserRepository userRepository;
    final private VerificationTokenRepository verificationTokenRepository;
    final private MailService mailService;
    final private AuthenticationManager authenticationManager;
    final private JwtProvider jwtProvider;

    @Transactional
    public void signup(RegisterRequest registerRequest) {
        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setMail(registerRequest.getEmail());
        user.setCreationTime(Instant.now());
        user.setEnabled(false);
        userRepository.save(user);
        String token = generateVerificationToken(user);

        mailService.sendMail(new EmailBody("Activate your account!",
                user.getMail(),
                "Thanks for signing up!"+
                "Click the link to activate your account "+
                "http://localhost:8080/auth/accountVerification/"+token));
    }




    private String generateVerificationToken(User user) {
        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(token);
        verificationToken.setUser(user);

        verificationTokenRepository.save(verificationToken);

        return token;

    }

    public void verifyAcc(String token) {
        Optional<VerificationToken> veryToken = verificationTokenRepository.findByToken(token);
        veryToken.orElseThrow(()->new CustomExeption("Invalid token!"));
        fetchUserAndEnable(veryToken.get());
    }
    @Transactional
    public void fetchUserAndEnable(VerificationToken verificationToken) {
        String username = verificationToken.getUser().getUsername();
        Optional<User> user = userRepository.findByUsername(username);
        user.orElseThrow(()->new CustomExeption("User not found"));
        user.get().setEnabled(true);
        userRepository.save(user.get());
    }

    public TokenResponse login(LoginRequest loginRequest) throws ParseException {
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername()
                , loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authenticate);
        String token = jwtProvider.generateToken(authenticate);
        jwtProvider.validateToken(token);
        System.out.println("TEST");
        return new TokenResponse(token, loginRequest.getUsername());

    }
}
