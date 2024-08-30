package com.example.JWTRefreshToken.exceptionhandling;

public class UserExists extends Exception {

    public UserExists(String msg) {
        super(msg);
    }
}