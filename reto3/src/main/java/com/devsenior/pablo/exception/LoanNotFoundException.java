package com.devsenior.pablo.exception;

public class LoanNotFoundException extends RuntimeException{
    public LoanNotFoundException(String message){
        super(message);
    }
}
