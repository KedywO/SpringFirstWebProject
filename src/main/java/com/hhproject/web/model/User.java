package com.hhproject.web.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import java.time.Instant;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class User {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private long id;
    @NotBlank(message = "Username must be filled")
    private String username;
    @NotBlank(message = "Username must be filled")
    private String password;
    @NotBlank(message = "Username must be filled")
    private String mail;
    private Instant creationTime;
    private boolean enabled;


}
