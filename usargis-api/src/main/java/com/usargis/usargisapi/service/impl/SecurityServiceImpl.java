package com.usargis.usargisapi.service.impl;

import com.usargis.usargisapi.repository.SecurityService;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.representations.AccessToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class SecurityServiceImpl implements SecurityService {
    @Override
    public boolean isSameUsernameThanAuthenticatedUser(String username) {
        username = username.toLowerCase();
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof KeycloakPrincipal) {
            AccessToken accessToken = ((KeycloakPrincipal) principal).getKeycloakSecurityContext().getToken();
            return accessToken.getPreferredUsername().toLowerCase().equals(username);
        }

        return false;
    }
}
