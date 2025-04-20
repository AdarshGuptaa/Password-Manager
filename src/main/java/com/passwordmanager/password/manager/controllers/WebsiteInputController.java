package com.passwordmanager.password.manager.controllers;



import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.passwordmanager.password.manager.exceptionHandlling.WebsiteUrlAlreadyExistsException;
import com.passwordmanager.password.manager.website.Website;
import com.passwordmanager.password.manager.website.WebsiteRepository;

@RestController
@RequestMapping("/websiteURL")
public class WebsiteInputController {

    @Autowired
    private WebsiteRepository websiteRepository;

    public record WebsiteInputDTO(String websiteName, String websiteURL){}
    
    @PostMapping("/add")
    public ResponseEntity<?> addWebsite(@RequestBody Website website){
        Optional<Website> existingWebsite = websiteRepository.findByUrl(website.getWebsiteURL());
        if (existingWebsite.isPresent()) {
            throw new WebsiteUrlAlreadyExistsException("Website Already Exists");
        }

        websiteRepository.save(website);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("Website Added");
    }

    @GetMapping("/getall")
    public ResponseEntity<List<Website>> getAllWebsites(){
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(websiteRepository.findAll());
    }
}
