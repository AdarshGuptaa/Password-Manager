package com.passwordmanager.password.manager.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class UserController {

    @PostMapping("/login")
    public ResponseEntity<String> login(String email, String password){
        return null;
    }

    @PostMapping("/signup")
    public ResponseEntity<String> signup(String email, String password){
        return null;
    }

    
    
}
