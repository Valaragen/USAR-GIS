package com.usargis.usargisapi.web.controller;

import com.usargis.usargisapi.util.Constant;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@PreAuthorize("hasRole('" + Constant.ADMIN_ROLE + "')")
@RequestMapping("/debug")
@RestController
public class DebugController {
    @GetMapping("/tokenAuthorities")
    public ResponseEntity findAllEvents() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        authentication.getAuthorities().iterator().forEachRemaining((e) -> log.info(String.valueOf(e)));

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
