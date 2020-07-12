package com.usargis.usargisapi.web.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ProhibitedActionException extends RuntimeException{
    public ProhibitedActionException(String message) {
        super(message);
    }
}
