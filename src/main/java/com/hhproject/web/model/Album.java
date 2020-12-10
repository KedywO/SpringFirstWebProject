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
import javax.validation.constraints.NotNull;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class Album {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private long id;
    @NotBlank(message = "Album name must be filled")
    @NotNull
    private String name;
    @NotBlank(message = "Artist name must be filled")
    @NotNull
    private String artist;
}
