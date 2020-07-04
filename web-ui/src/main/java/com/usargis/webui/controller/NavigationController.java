package com.usargis.webui.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NavigationController {

    @GetMapping("/")
    public String getHomepage() {
        return "homePage";
    }

    @GetMapping("/user-secured")
    public String getUserSecured() {
        return "userSecured";
    }

    @GetMapping("/member-secured")
    public String getMemberSecuredPage() {
        return "memberSecured";
    }

}
