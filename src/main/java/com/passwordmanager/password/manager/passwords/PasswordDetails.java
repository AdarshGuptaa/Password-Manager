package com.passwordmanager.password.manager.passwords;

import java.time.Instant;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.passwordmanager.password.manager.user.User;
import com.passwordmanager.password.manager.website.Website;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="passwords")
@EntityListeners(AuditingEntityListener.class)
public class PasswordDetails {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id")
    private Long passwordId;

    @ManyToOne
    @Column(name="user_id")
    private User user;

    @ManyToOne
    @Column(name="website_id")
    private Website website;
    
    @Column(name="website_username")
    private String websiteUsername;

    @Column(name="website_password")
    private String websitePassword;

    @CreatedDate
    @Column(name="created_at")
    private Instant creationDate;

    @CreatedDate
    @Column(name="updated_at")
    private Instant updateDate;

    public Long getPasswordId() {
        return passwordId;
    }

    public void setPasswordId(Long passwordId) {
        this.passwordId = passwordId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Website getWebsite() {
        return website;
    }

    public void setWebsite(Website website) {
        this.website = website;
    }

    public String getWebsiteUsername() {
        return websiteUsername;
    }

    public void setWebsiteUsername(String websiteUsername) {
        this.websiteUsername = websiteUsername;
    }

    public String getWebsitePassword() {
        return websitePassword;
    }

    public void setWebsitePassword(String websitePassword) {
        this.websitePassword = websitePassword;
    }

    public Instant getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Instant creationDate) {
        this.creationDate = creationDate;
    }

    public Instant getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Instant updateDate) {
        this.updateDate = updateDate;
    }


}
