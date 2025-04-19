package com.passwordmanager.password.manager.user;


import java.time.Instant;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="user_details")
@EntityListeners(AuditingEntityListener.class)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_id")
    private Long id;
    
    @Column(name="email")
    private String username;

    @Column(name="password")
    private String password;

    @Column(name="created_at")
    @CreatedDate
    private Instant creationDate;

    @Column(name="total_password")
    private Long totalPasswords;

    public Long getTotalPasswords() {
        return totalPasswords;
    }

    public void setTotalPasswords(Long totalPasswords) {
        this.totalPasswords = totalPasswords;
    }

    public User(){
        
    }

    public User(Long id, String username, String password, Instant creationDate) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.creationDate = creationDate;
        this.totalPasswords = 0L;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Instant getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Instant creationDate) {
        this.creationDate = creationDate;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }




}
