package com.passwordmanager.password.manager.controllers;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

import com.passwordmanager.password.manager.exceptionHandlling.WebsiteUrlAlreadyExistsException;
import com.passwordmanager.password.manager.exceptionHandlling.WebsiteUrlConnectionFailed;
import com.passwordmanager.password.manager.passwords.AESEncrypter;
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
    private PasswordRepository passwordRepository;

    @Autowired
    private WebsiteRepository websiteRepository;

    public record DeletePasswordDTO(Long passwordId) {
    }

    public record PasswordDTO(Long passwordId, String websiteUrl, String websiteUsername, String websitePassword) {
    }

    private User getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found"));

        return user;
    }

    @PostMapping("/addpassword")
    public ResponseEntity<?> addPassword(@RequestBody PasswordDTO passwordDTO) {
       try{ User user = getUser();
        PasswordDetails passwordDetails = new PasswordDetails();

        URI siteUri = new URI(passwordDTO.websiteUrl());
        URL siteUrl = siteUri.toURL();
        HttpURLConnection connection = (HttpURLConnection) siteUrl.openConnection();
        int responseCode = connection.getResponseCode();
        String url = siteUri.getScheme() + "://" + siteUri.getHost();

        Optional<Website> existingWebsite = websiteRepository.findByWebsiteURL(url);

        if (existingWebsite.isPresent()) {
            passwordDetails.setWebsite(existingWebsite.get());
        } else {
            Website web = new Website();
            System.out.println(responseCode);
            if (responseCode == 403 || responseCode == 401 || (responseCode >= 200 && responseCode < 300)) {
                String host = siteUri.getHost();
                if (host.startsWith("www.")) {
                    host = host.substring(4);
                }
                String websiteName = host.split("\\.")[0].substring(0, 1).toUpperCase()
                        + host.split("\\.")[0].substring(1);
                web.setWebsiteName(websiteName);
                web.setWebsiteURL(url);
                websiteRepository.save(web);
            } else {
                throw new WebsiteUrlConnectionFailed("Website did not respond in time");
            }
            passwordDetails.setWebsite(web);
        }

        passwordDetails.setUser(user);
        passwordDetails.setWebsiteUsername(passwordDTO.websiteUsername());
        try {
            passwordDetails.setWebsitePassword(com.passwordmanager.password.manager.passwords.AESEncrypter
                    .encrypt(user.getPassword(), passwordDTO.websitePassword()));
        } catch (Exception e) {
            System.out.println("Last error");
        }
        passwordRepository.save(passwordDetails);
        if(user.getTotalPasswords() == null){user.setTotalPasswords(1L);}
        else{user.setTotalPasswords(user.getTotalPasswords() + 1);}
        return ResponseEntity.status(HttpStatus.OK).body("Password Added Successfully!");}
        catch (URISyntaxException | IOException e) {
            System.out.println("invalid url");
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Invalid URL");
        } catch (WebsiteUrlAlreadyExistsException e) {
            System.out.println("url exists");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (WebsiteUrlConnectionFailed e) {
            System.out.println("failed connection");
            return ResponseEntity.status(HttpStatus.GATEWAY_TIMEOUT).body(e.getMessage());
        }
        
}
    
    

    @DeleteMapping("/deletepassword")
    public ResponseEntity<?> deletePassword(@RequestBody DeletePasswordDTO deletePasswordDTO) {
        try{
            User user = getUser();
        PasswordDetails passwordDetails = passwordRepository.findById(deletePasswordDTO.passwordId())
                .orElseThrow(() -> new UsernameNotFoundException("Password Does not exist"));
        passwordRepository.delete(passwordDetails);
        if(user.getTotalPasswords() == null){user.setTotalPasswords(0L);}
        else{user.setTotalPasswords(user.getTotalPasswords() - 1);}
        return ResponseEntity.status(HttpStatus.OK).body("Password Deleted Successfully!");
        }
        catch(Exception e){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Password Deleted Successfully!");
        }
    }

    @GetMapping("/getallpasswords")
public ResponseEntity<List<PasswordDTO>> getAllPasswords() {
    try {
        User user = getUser();
        List<PasswordDetails> encryptedUserPasswords = passwordRepository.findAllByUser(user)
            .orElseThrow(() -> new UsernameNotFoundException("Passwords couldn't be found!"));

        List<PasswordDTO> decryptedUserPasswordList = new ArrayList<>();
        for (PasswordDetails encryptedPassword : encryptedUserPasswords) {
            decryptedUserPasswordList.add(new PasswordDTO(
                encryptedPassword.getPasswordId(),
                encryptedPassword.getWebsite().getWebsiteURL(),
                encryptedPassword.getWebsiteUsername(),
                AESEncrypter.decrypt(user.getPassword(), encryptedPassword.getWebsitePassword())
            ));
        }
        return ResponseEntity.ok(decryptedUserPasswordList);

    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}
    

    @PutMapping("/updatepassword")
    public ResponseEntity<?> updatePassword(@RequestBody PasswordDTO passwordDTO) throws Exception {
        User user = getUser();
        PasswordDetails passwordDetails = passwordRepository.findById(passwordDTO.passwordId())
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found"));
        passwordDetails.setWebsiteUsername(passwordDTO.websiteUsername());
        passwordDetails.setWebsitePassword(AESEncrypter.encrypt(user.getPassword(), passwordDTO.websitePassword()));
        passwordDetails.setUpdateDate(Instant.now());
        passwordRepository.save(passwordDetails);

        return ResponseEntity.status(HttpStatus.OK).body("Password Updated");
    }

}
