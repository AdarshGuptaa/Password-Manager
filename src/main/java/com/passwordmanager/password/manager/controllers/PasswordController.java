package com.passwordmanager.password.manager.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.passwordmanager.password.manager.passwords.AESEncrypter;
import com.passwordmanager.password.manager.passwords.PasswordDetails;
import com.passwordmanager.password.manager.passwords.PasswordRepository;
import com.passwordmanager.password.manager.user.User;
import com.passwordmanager.password.manager.user.UserRepository;
import com.passwordmanager.password.manager.website.Website;
@RestController
@RequestMapping("/passwords")
public class PasswordController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordRepository passwordRepository;

    public record DeletePasswordDTO(Long passwordId){}
    public record PasswordDTO(Long passwordId, Website website, String websiteUsername, String websitePassword){}



    private User getUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("User Not Found"));
        
        return user;
    }

    @PostMapping("/addpassword")
    public ResponseEntity<?> addPassword(@RequestBody  PasswordDTO passwordDTO) throws Exception{
        User user = getUser();
        PasswordDetails passwordDetails = new PasswordDetails();
        passwordDetails.setUser(user);
        passwordDetails.setWebsite(passwordDTO.website());
        passwordDetails.setWebsiteUsername(passwordDTO.websiteUsername());
        passwordDetails.setWebsitePassword(com.passwordmanager.password.manager.passwords.AESEncrypter.encrypt(user.getPassword(), passwordDTO.websitePassword()));
        passwordRepository.save(passwordDetails);
        return ResponseEntity.status(HttpStatus.OK).body("Password Added Successfully!");
    }
    

    @DeleteMapping("/deletepassword")
    public ResponseEntity<?> deletePassword(@RequestBody DeletePasswordDTO deletePasswordDTO){
        PasswordDetails passwordDetails = passwordRepository.findById(deletePasswordDTO.passwordId())
        .orElseThrow(()-> new UsernameNotFoundException("Password Does not exist"));
        passwordRepository.delete(passwordDetails);
        return ResponseEntity.status(HttpStatus.OK).body("Password Deleted Successfully!");
    }
    
    @GetMapping("/getallpasswords")
    public ResponseEntity<List<PasswordDTO>> getAllPasswords() throws Exception{
        User user = getUser();
        List<PasswordDetails> encryptedUserPasswords = passwordRepository.findAllByUser(user)
        .orElseThrow(() -> new UsernameNotFoundException("Passwords couldn't be found!"));
        for(PasswordDetails p : encryptedUserPasswords){
            System.out.println(p);
        }
        List<PasswordDTO> decryptedUserPasswordList= new ArrayList<>();
        for(PasswordDetails encryptedPassword : encryptedUserPasswords){
            decryptedUserPasswordList.add(new PasswordDTO(
            encryptedPassword.getPasswordId()
            ,encryptedPassword.getWebsite()
            , encryptedPassword.getWebsiteUsername()
            ,com.passwordmanager.password.manager.passwords.AESEncrypter.decrypt(user.getPassword(), encryptedPassword.getWebsitePassword())));
        }
        return ResponseEntity.status(HttpStatus.OK).body(decryptedUserPasswordList);
    }

    @PutMapping("/updatepassword")
    public ResponseEntity<?> updatePassword(@RequestBody PasswordDTO passwordDTO) throws Exception{
        User user = getUser();
        PasswordDetails passwordDetails =   passwordRepository.findById(passwordDTO.passwordId())
                                            .orElseThrow(() -> new UsernameNotFoundException("User Not Found"));

        passwordDetails.setWebsite(passwordDTO.website());
        passwordDetails.setWebsiteUsername(passwordDTO.websiteUsername());
        passwordDetails.setWebsitePassword(AESEncrypter.encrypt(user.getPassword(), passwordDTO.websitePassword()));
        passwordRepository.save(passwordDetails);

        return ResponseEntity.status(HttpStatus.OK).body("Password Updated");
    }


}
