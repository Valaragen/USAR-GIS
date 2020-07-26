package com.usargis.usargisapi.service.contract;

import org.keycloak.representations.AccessToken;

public interface SecurityService {
    boolean isSameUsernameThanAuthenticatedUser(String username);

    String getUsernameFromToken();

    AccessToken getKeycloakAccessToken();
}
