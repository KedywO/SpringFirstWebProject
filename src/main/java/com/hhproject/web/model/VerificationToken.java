package com.hhproject.web.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
@Entity
public class VerificationToken {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private long tokenId;
    @OneToOne(fetch = FetchType.LAZY)
    private User user;
    private String token;
    private Instant expiryDate;



}
