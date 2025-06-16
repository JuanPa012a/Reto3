package com.devsenior.pablo.exception;

public class BookLentYetException extends RuntimeException{
    public BookLentYetException(){
        super("Este libro se encuentra prestado");
    }
}