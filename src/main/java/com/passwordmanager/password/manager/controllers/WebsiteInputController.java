package com.passwordmanager.password.manager.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.passwordmanager.password.manager.website.Website;
import com.passwordmanager.password.manager.website.WebsiteRepository;

@RestController
@RequestMapping("/websiteURL")
public class WebsiteInputController {

    @Autowir
    private WebsiteRepository websiteRepository;

    public record WebsiteInputDTO(String websiteName, String websiteURL){}
    
    @PostMapping("/add")
    public ResponseEntity<?> addWebsite(@RequestBody WebsiteInputDTO websiteInputDTO){
        Optional<Website> existingWebsite = websiteRepository.findByUrl(websiteInputDTO.websiteURL);
    }

    @GetMapping("/getall")
    public ResponseEntity<?> getAllWebsites(){
        
    }
}
