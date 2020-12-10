package com.hhproject.web.exceptions;

import com.hhproject.web.model.Album;

import java.util.function.Supplier;

public class CustomExeption extends RuntimeException implements Supplier<Album> {
    public CustomExeption(String message){
        super(message);
    }


    @Override
    public Album get() {
        return null;
    }
}
