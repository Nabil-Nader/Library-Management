package com.library.management.exception;

public class BookNotAvailableException extends RuntimeException{
    public BookNotAvailableException(String message) {
        super(message);
    }
}