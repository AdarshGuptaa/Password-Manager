# 🔐 Password Manager Web App

A full-stack **Password Manager** built from the ground up by me — a Computer Science student exploring how secure applications are truly made.
This is **not just another form-and-save project**, nor is it an incomplete prototype. It's a complete system built following every major step of a production cycle.

### 🚧 Built From Scratch (Following these setps to production):
- 📐 **Database Schema Design**
- 🔧 **Backend API Development (Spring Boot)**
- 🔐 **Encryption & Hashing (AES + BCrypt)**
- 🎨 **Frontend UI Construction (React + Tailwind + Bootstrap)**
- 🔗 **Frontend–Backend Integration**
- 🧪 **Testing & Debugging**
- ☁️ **Deployment on AWS (RDS + EC2)**

---

## 🌍 Access It Online

📍 **Live Website**:  
[`http://ec2-43-205-230-85.ap-south-1.compute.amazonaws.com:8080/`](http://ec2-43-205-230-85.ap-south-1.compute.amazonaws.com:8080/)

> ⚠️ **Note:** The site is served over **HTTP** (not HTTPS). If it's inaccessible, ensure your browser doesn't force HTTPS.

🕒 **Uptime Guarantee**:  
The hosted app will remain live on AWS until **June 2025**.

---

## 📌 Key Features

- 🔒 Store and manage credentials securely using **AES encryption** for saved passwords and **BCrypt hashing** for user login passwords
- 🔑 **JWT-based Authentication** for stateless and secure user sessions
- 🧠 RESTful **Spring Boot backend**
- 💾 **AWS RDS (MySQL)** for persistent storage
- 🎨 Clean, responsive UI with **React**, **Tailwind CSS**, and **React-Bootstrap**
- 🚀 **Hosted backend** on **AWS EC2 (Ubuntu)** with `systemd` and NGINX

---

## 🛠 Tech Stack

### Backend 🧠
- **Java 21 + Spring Boot**
- **Spring Web**, **Spring Security**, **Spring Data JPA**
- **MySQL** (AWS RDS)
- **JWT** for authentication
- **BCrypt** for password hashing
- **AES** for encrypting stored passwords

### Frontend 🎨
- **React.js**
- **React-Bootstrap**
- **Tailwind CSS**

### Deployment & Hosting 🚀
- **AWS EC2** (Ubuntu server)
- **AWS RDS** (MySQL)
- **NGINX + systemd** for backend process management

---

## 🔌 API Endpoints

### 🔑 AuthController

| Method | Endpoint            | Description |
|--------|---------------------|-------------|
| POST   | `/api/auth/signup`  | Register new users. Passwords are **hashed using BCrypt** before saving. |
| POST   | `/api/auth/login`   | Login users and return a **JWT token** upon successful authentication. |

### 🔐 PasswordDetailsController

| Method | Endpoint                          | Description |
|--------|-----------------------------------|-------------|
| POST   | `/api/password/addpassword`       | Add new credentials. Passwords are **AES-encrypted** before storage. |
| GET    | `/api/password/getallpasswords`   | Fetch all saved credentials for the authenticated user. |
| DELETE | `/api/password/deletepassword`    | Delete a password entry by ID. |
| PUT    | `/api/password/updatepassword`    | Update existing credentials (re-encrypts with AES if password is changed). |

> 🔐 All routes (except login and signup) require a valid **JWT** in the `Authorization` header.

---

## 📸 UI Screenshots

### 🔐 Login Page
---
![Login Page](/login-page.png)
---
### 📋 Your Passwords
---
![Your Passwords Page](/your-passwords-page.png)
### ➕ Add a Password
---
![Add Password Page](/add-password-page.png)
### 🧭 About Page
---
![Aout Page](/about-page.png)
## 🧠 Security Architecture

- **JWT Authentication** – Secures endpoints and ensures stateless user sessions
- **BCrypt** – Hashes user login passwords for safe storage
- **AES Encryption** – Encrypts each saved password; only decrypted when fetched
- **CORS + Spring Security Filters** – Ensures only verified users can access or modify data

---

## 🌐 Hosting (AWS Cloud)

- 🔁 Spring Boot backend runs on an **Ubuntu EC2 instance**
- 🔐 Secured with **NGINX reverse proxy** (optional HTTPS setup)
- 🗃️ **MySQL database** hosted on **AWS RDS**
- 🧠 Application managed with **systemd service**

---

## 🚧 Planned Improvements

- [ ] Password strength meter
- [ ] Secure password generator
- [ ] Chrome/Browser Extension
- [ ] Forgot password reset mechanism

---

## 🙋‍♂️ About Me

I am a first-year Computer Science student(2025-28) @ABVIIITM Gwalior. 
This project helped me apply everything I’ve learned about authentication, encryption, REST APIs, deployment, and frontend integration.

📫 [LinkedIn – Adarsh Gupta](www.linkedin.com/in/adarsh-gupta-a4ba60322)  
📁 [GitHub Repo](github.com/AdarshGuptaa/Password-Manager)
