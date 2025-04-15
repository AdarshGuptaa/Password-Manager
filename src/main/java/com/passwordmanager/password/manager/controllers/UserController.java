package com.passwordmanager.password.manager.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class UserController {
    public ResponseEntity<String> login(String username, String password){
        return null;
    }
}
