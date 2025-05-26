package com.passwordmanager.password.manager.controllers;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
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

import io.jsonwebtoken.io.IOException;

@RestController
@RequestMapping("/websiteURL")
public class WebsiteInputController {

    @Autowired
    private WebsiteRepository websiteRepository;

    public record WebsiteInputDTO(String websiteName, String websiteURL) {
    }

    @PostMapping("/add")
    public ResponseEntity<?> addWebsite(@RequestBody WebsiteInputDTO website) {
        try {
            if (website.websiteURL() == null || website.websiteURL().trim().isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Website URL is required");
            }
            URI siteUri = new URI(website.websiteURL());
            URL siteUrl = siteUri.toURL();

            HttpURLConnection connection = (HttpURLConnection) siteUrl.openConnection();
            int responseCode = connection.getResponseCode();
            String url = siteUri.getScheme() + "://" + siteUri.getHost();

            Optional<Website> existingWebsite = websiteRepository.findByWebsiteURL(url);
            if (existingWebsite.isPresent()) {
                throw new WebsiteUrlAlreadyExistsException("Website Already Exists");
            }

            System.out.println(responseCode);
            if (responseCode == 403 || responseCode == 401 || (responseCode >= 200 && responseCode < 300)) {
                Website web = new Website();
                String host = siteUri.getHost();
                if (host.startsWith("www.")) {
                    host = host.substring(4);
                }
                String websiteName = host.split("\\.")[0].substring(0, 1).toUpperCase()
                        + host.split("\\.")[0].substring(1);
                web.setWebsiteName(websiteName);
                web.setWebsiteURL(url);
                websiteRepository.save(web);
                getAllWebsites();
            } else {
                throw new WebsiteUrlConnectionFailed("Website did not respond in time");
            }

            return ResponseEntity.status(HttpStatus.ACCEPTED).body("Website Added");
        } catch (URISyntaxException | IOException e) {
            System.out.println("invalid url");
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Invalid URL");
        } catch (WebsiteUrlAlreadyExistsException e) {
            System.out.println("url exists");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (WebsiteUrlConnectionFailed e) {
            System.out.println("failed connection");
            return ResponseEntity.status(HttpStatus.GATEWAY_TIMEOUT).body(e.getMessage());
        } catch (MalformedURLException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        } catch (java.io.IOException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        }

    }

    @GetMapping("/getall")
    public ResponseEntity<List<Website>> getAllWebsites() {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(websiteRepository.findAll());
    }
}
