package com.example.kinoarenaproject.model.exceptions;

public class UnautorizedException extends RuntimeException{
    public UnautorizedException(String msg){
        super(msg);
    }
}
