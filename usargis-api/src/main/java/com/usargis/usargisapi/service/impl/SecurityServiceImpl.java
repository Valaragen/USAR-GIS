package com.usargis.usargisapi.service.impl;

import com.usargis.usargisapi.service.contract.SecurityService;
import com.usargis.usargisapi.util.ErrorConstant;
import com.usargis.usargisapi.web.exception.AccessForbiddenException;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.exceptions.TokenNotActiveException;
import org.keycloak.representations.AccessToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

@Service
public class SecurityServiceImpl implements SecurityService {
    @Override
    public boolean isSameUsernameThanAuthenticatedUser(String username) {
        username = username.toLowerCase();
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof KeycloakPrincipal) {
            AccessToken accessToken = ((KeycloakPrincipal) principal).getKeycloakSecurityContext().getToken();
            return accessToken.getPreferredUsername().toLowerCase().equals(username);
        } else {
            throw new AccessForbiddenException(ErrorConstant.ERROR_READ_TOKEN_EXCEPTION);
        }
    }

    @Override
    public String getUsernameFromToken() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof KeycloakPrincipal) {
            AccessToken accessToken = ((KeycloakPrincipal) principal).getKeycloakSecurityContext().getToken();
            return accessToken.getPreferredUsername().toLowerCase();
        } else {
            throw new AccessForbiddenException(ErrorConstant.ERROR_READ_TOKEN_EXCEPTION);
        }
    }
}
