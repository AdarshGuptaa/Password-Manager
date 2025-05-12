package com.passwordmanager.password.manager.website;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="websites")
public class Website {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="website_id")
    private Long websiteId;

    @Column(name="name")
    private String websiteName;

    @Column(name="url")
    private String websiteURL;

    public Website(Long websiteId, String websiteName, String websiteURL) {
        this.websiteId = websiteId;
        this.websiteName = websiteName;
        this.websiteURL = websiteURL;
    }

    public Website(){
        
    }

    public Long getWebsiteId() {
        return websiteId;
    }

    public void setWebsiteId(Long websiteId) {
        this.websiteId = websiteId;
    }

    public String getWebsiteName() {
        return websiteName;
    }

    public void setWebsiteName(String websiteName) {
        this.websiteName = websiteName;
    }

    public String getWebsiteURL() {
        return websiteURL;
    }

    public void setWebsiteURL(String websiteURL) {
        this.websiteURL = websiteURL;
    }
    
}
