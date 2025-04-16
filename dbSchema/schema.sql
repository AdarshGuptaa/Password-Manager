CREATE DATABASE IF NOT EXISTS password_managerdb;
USE password_managerdb;

-- User table
CREATE TABLE IF NOT EXISTS user_details (
    user_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    email VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    creation_date DATE,
    total_passwords BIGINT
) ENGINE=InnoDB;

-- Website metadata table
CREATE TABLE IF NOT EXISTS websites (
    website_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    url VARCHAR(2200) NOT NULL
) ENGINE=InnoDB;

-- Passwords table linking user and website
CREATE TABLE IF NOT EXISTS passwords (
    password_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT,
    website_id BIGINT,
    website_username VARCHAR(255),
    website_password VARCHAR(255),
    creation_date DATE,
    FOREIGN KEY (user_id) REFERENCES user_details(user_id) ON DELETE CASCADE,
    FOREIGN KEY (website_id) REFERENCES websites(website_id) ON DELETE CASCADE
) ENGINE=InnoDB;
