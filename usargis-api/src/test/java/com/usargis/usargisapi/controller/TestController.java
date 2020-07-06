package com.usargis.usargisapi.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @GetMapping("/unsecured")
    public String unsecured() {
        return "Unsecured";
    }

    @GetMapping("/hello")
    public String helloWorld() {
        return "Hello world !";
    }

    @GetMapping("/member")
    public String helloMember() {
        return "Hello member !";
    }

    @GetMapping("/leader")
    public String helloLeader() {
        return "Hello leader !";
    }

    @GetMapping("/admin")
    public String helloAdmin() {
        return "Hello admin !";
    }
}
