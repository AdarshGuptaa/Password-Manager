package com.passwordmanager.password.manager.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.passwordmanager.password.manager.passwords.PasswordDetails;
import com.passwordmanager.password.manager.user.User;
import com.passwordmanager.password.manager.user.UserRepository;
import com.passwordmanager.password.manager.website.Website;
import com.passwordmanager.password.manager.website.WebsiteRepository;

@RestController
@RequestMapping("/passwords")
public class PasswordController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WebsiteRepository websiteRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    private User getUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("User Not Found"));
        
        return user;
    }

    @PostMapping("/addpassword")
    public ResponseEntity<?> addPassword(@RequestBody PasswordDetails passwordDetailsCreds){
        User user = getUser();
        Website website = websiteRepository.findById(passwordDetailsCreds.getWebsite().getWebsiteId())
        .orElseThrow(() -> new UsernameNotFoundException("Website Not Found"));

        PasswordDetails passwordDetails = new PasswordDetails();
        passwordDetails.setUser(user);
        passwordDetails.setWebsite(website);
        passwordDetails.setWebsiteUsername(passwordEncoder.encode(passwordDetailsCreds.getWebsiteUsername()));
        passwordDetails.setWebsitePassword(passwordEncoder.encode(passwordDetailsCreds.getWebsitePassword()));

        return ResponseEntity.status(HttpStatus.OK).body("Password Added Successfully!");
    }
    

    @DeleteMapping("/deletepassword")
    public ResponseEntity<?> deletePassword(){
        return null;
    }
    
    @GetMapping("/getallpasswords")
    public ResponseEntity<?> getAllPasswords(){
        return null;
    }

    @PutMapping("/updatemapping")
    public ResponseEntity<?> updatePassword(){
        return null;
    }
}
