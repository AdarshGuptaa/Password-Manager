package com.passwordmanager.password.manager.controllers;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
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
import com.passwordmanager.password.manager.exceptionHandlling.WebsiteUrlConnectionFailed;
import com.passwordmanager.password.manager.website.Website;
import com.passwordmanager.password.manager.website.WebsiteRepository;

@RestController
@RequestMapping("/websiteURL")
public class WebsiteInputController {

    @Autowired
    private WebsiteRepository websiteRepository;

    public record WebsiteInputDTO(String websiteName, String websiteURL) {
    }

    @PostMapping("/add")
    public ResponseEntity<?> addWebsite(@RequestBody Website website) throws URISyntaxException, IOException{
        try{
            Optional<Website> existingWebsite = websiteRepository.findByWebsiteURL(website.getWebsiteURL());
        if (existingWebsite.isPresent()) {
            throw new WebsiteUrlAlreadyExistsException("Website Already Exists");
        }

        URL siteUrl = new URI(website.getWebsiteURL()).toURL();
        HttpURLConnection connection = (HttpURLConnection)siteUrl.openConnection();
        int responseCode = connection.getResponseCode();
        if(responseCode >= 300 || responseCode <= 199){
            throw new WebsiteUrlConnectionFailed("Website did not respond in time");
        }

        websiteRepository.save(website);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("Website Added");
    }
    catch(URISyntaxException | IOException e){
        return ResponseEntity.status(HttpStatus.CONFLICT).body("Invalid URL");
    }

        }

    @GetMapping("/getall")
    public ResponseEntity<List<Website>> getAllWebsites() {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(websiteRepository.findAll());
    }
}
