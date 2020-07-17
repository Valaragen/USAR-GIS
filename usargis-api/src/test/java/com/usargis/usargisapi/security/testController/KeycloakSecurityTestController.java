package com.usargis.usargisapi.security.testController;

import com.usargis.usargisapi.util.Constant;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/keycloak-test")
public class KeycloakSecurityTestController {

    @GetMapping("/admin")
    @PreAuthorize("hasRole('" + Constant.ADMIN_ROLE + "')")
    public ResponseEntity<String> adminAccess() {
        return new ResponseEntity<>("Authorized", HttpStatus.OK);
    }

    @GetMapping("/leader")
    @PreAuthorize("hasRole('" + Constant.LEADER_ROLE + "')")
    public ResponseEntity<String> leaderAccess() {
        return new ResponseEntity<>("Authorized", HttpStatus.OK);
    }

    @GetMapping("/member")
    @PreAuthorize("hasRole('" + Constant.MEMBER_ROLE + "')")
    public ResponseEntity<String> memberAccess() {
        return new ResponseEntity<>("Authorized", HttpStatus.OK);
    }

    @GetMapping("/sameuser/{user}")
    @PreAuthorize("@securityServiceImpl.isSameUsernameThanAuthenticatedUser(#user)")
    public ResponseEntity<String> sameUserAccess(@PathVariable String user) {
        return new ResponseEntity<>("Authorized", HttpStatus.OK);
    }

}

