package com.passwordmanager.password.manager.exceptionHandlling;

public class WebsiteUrlConnectionFailed extends RuntimeException{
    public WebsiteUrlConnectionFailed(String message){
        super(message);
    }
}
