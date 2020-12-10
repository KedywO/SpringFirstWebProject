package com.hhproject.web.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.Instant;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class Order {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private long id;
    @NotBlank(message = "Username must be filled")
    private double price;
    @ManyToOne
    @JoinColumn (name = "userId" , referencedColumnName = "id")
    private User user;
    @ManyToOne
    @JoinColumn (name = "albumId", referencedColumnName = "id")
    private Album album;
    private Instant orderTime;
    private String address;


}
