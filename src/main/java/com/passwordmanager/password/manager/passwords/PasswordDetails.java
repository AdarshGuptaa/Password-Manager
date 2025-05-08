package com.passwordmanager.password.manager.passwords;

import com.passwordmanager.password.manager.user.User;
import com.passwordmanager.password.manager.website.Website;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="passwords")
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
    

}
