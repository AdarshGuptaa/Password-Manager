package com.passwordmanager.password.manager.exceptionHandlling;

public class WebsiteUrlAlreadyExistsException extends RuntimeException{
    public WebsiteUrlAlreadyExistsException(String message){
        super(message);
    }

}
