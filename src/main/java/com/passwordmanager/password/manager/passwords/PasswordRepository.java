package com.passwordmanager.password.manager.passwords;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.passwordmanager.password.manager.user.User;
import com.passwordmanager.password.manager.website.Website;

public interface PasswordRepository extends JpaRepository<PasswordDetails, Long>{
    Optional<PasswordDetails> findByWebsite(Website website);
    Optional<PasswordDetails> findByUser(User user);
    Optional<List<PasswordDetails>> findAllByUser(User user);
}
