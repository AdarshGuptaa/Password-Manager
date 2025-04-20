package com.passwordmanager.password.manager.website;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface WebsiteRepository extends JpaRepository<Website,Long>{
    Optional<String> findByName(String websiteName);
    Optional<String> findByUrl(String websiteURL);
}
