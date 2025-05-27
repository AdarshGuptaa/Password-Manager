package com.passwordmanager.password.manager.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FrontEndController {

    @GetMapping("/{path:[^\\.]*}")
    public String redirectSingle() {
        return "forward:/index.html";
    }
    @GetMapping({"/yourpasswords", "/addpassword", "/about"})
    public String getIndex() {
        return "forward:/index.html";
    }
}

