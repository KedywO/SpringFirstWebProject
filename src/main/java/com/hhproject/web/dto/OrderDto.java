package com.hhproject.web.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {
    private long id;
    private long albumId;
    private Instant orderTime;
    private String address;
    private double price;


}
