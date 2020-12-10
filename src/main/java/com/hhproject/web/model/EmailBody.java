package com.hhproject.web.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailBody {
    private String subject;
    private String recipient;
    private String body;
}
