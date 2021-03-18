package com.account.exceptionHandler;

public class UserNotFoundInSystem extends Exception{
    public UserNotFoundInSystem(String message) {
        super(message);
    }
}
