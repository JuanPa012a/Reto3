package com.devsenior.pablo.exception;

public class MaxLoansException extends RuntimeException{
    public MaxLoansException(){
        super("Ya tiene la cantidad máxima de prestamos");
    }    
}
