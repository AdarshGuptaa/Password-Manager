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
import com.passwordmanager.password.manager.passwords.PasswordRepository;
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

    @Autowired
    private PasswordRepository passwordRepository;

    public record DeletePasswordDTO(Long passwordId){}
    public record PasswordDTO(Long webisteId, String websiteUsername, String websitePassword){}


    private User getUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("User Not Found"));
        
        return user;
    }

    @PostMapping("/addpassword")
    public ResponseEntity<?> addPassword(@RequestBody  PasswordDTO passwordDTO){
        User user = getUser();
        Website website = websiteRepository.findById(passwordDTO.webisteId())
        .orElseThrow(() -> new UsernameNotFoundException("Website Not Found"));
        PasswordDetails passwordDetails = new PasswordDetails();
        passwordDetails.setUser(user);
        passwordDetails.setWebsite(website);
        passwordDetails.setWebsiteUsername(passwordEncoder.encode(passwordDTO.websiteUsername()));
        passwordDetails.setWebsitePassword(passwordEncoder.encode(passwordDTO.websitePassword()));
        passwordRepository.save(passwordDetails);

        return ResponseEntity.status(HttpStatus.OK).body("Password Added Successfully!");
    }
    

    @DeleteMapping("/deletepassword")
    public ResponseEntity<?> deletePassword(@RequestBody deletePasswordDTO deletePasswordDTO){
        PasswordDetails passwordDetails = passwordRepository.findById(deletePasswordDTO.passwordId())
        .orElseThrow(()-> new UsernameNotFoundException("Password Does not exist"));
        passwordRepository.delete(passwordDetails);
        return ResponseEntity.status(HttpStatus.OK).body("Password Deleted Successfully!");
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
