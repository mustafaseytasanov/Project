package com.example.project.exception;


/**
 * Class ServerNotFoundException
 * @author Mustafa
 * @version 1.0
 */
public class ServerNotFoundException extends RuntimeException {
    public ServerNotFoundException(String message) {
        super(message);
    }
}
