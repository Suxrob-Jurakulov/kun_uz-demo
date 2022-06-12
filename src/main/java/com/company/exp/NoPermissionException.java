package com.company.exp;

public class NoPermissionException extends RuntimeException{
    public NoPermissionException(String message) {
        super(message);
    }
}
