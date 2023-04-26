package ru.kata.spring.boot_security.demo.exceptions;

public class EmailFormatException extends RuntimeException {
    public EmailFormatException(String message) {
        super(message);
    }
}
