package com.usargis.usargisapi.web.controller;

import com.usargis.usargisapi.service.contract.SecurityService;
import com.usargis.usargisapi.service.impl.SecurityServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.representations.AccessToken;
import org.mockito.Mockito;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

class SecurityServiceImplTest {

    private SecurityService objectToTest;

    @BeforeEach
    void setup() {
        objectToTest = new SecurityServiceImpl();
    }

    @Nested
    class isSameUsernameThanAuthenticatedUserTest {
        private String tokenUsername = "currentuser";
        private Authentication authentication = Mockito.mock(Authentication.class);
        private KeycloakPrincipal keycloakPrincipal = Mockito.mock(KeycloakPrincipal.class);
        private KeycloakSecurityContext keycloakSecurityContext = Mockito.mock(KeycloakSecurityContext.class);
        private AccessToken accessToken = Mockito.mock(AccessToken.class);
        private SecurityContext securityContext = Mockito.mock(SecurityContext.class);

        @BeforeEach
        void setup() {
            //Mock context
            Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
            Mockito.when(authentication.getPrincipal()).thenReturn(keycloakPrincipal);
            Mockito.when(keycloakPrincipal.getKeycloakSecurityContext()).thenReturn(keycloakSecurityContext);
            Mockito.when(keycloakSecurityContext.getToken()).thenReturn(accessToken);
        }

        @Test
        void isSameUsernameThanAuthenticatedUser_tokenUsernameIsDifferentThanGivenUsername_returnFalse() {
            String givenUsername = "randomUsername";
            Mockito.when(accessToken.getPreferredUsername()).thenReturn(tokenUsername);

            SecurityContextHolder.setContext(securityContext);

            boolean result = objectToTest.isSameUsernameThanAuthenticatedUser(givenUsername);

            Assertions.assertThat(result).isFalse();
        }

        @Test
        void isSameUsernameThanAuthenticatedUser_tokenUsernameIsSameThanGivenUsername_returnTrue() {
            String givenUsername = tokenUsername;
            Mockito.when(accessToken.getPreferredUsername()).thenReturn(tokenUsername);

            SecurityContextHolder.setContext(securityContext);

            boolean result = objectToTest.isSameUsernameThanAuthenticatedUser(givenUsername);

            Assertions.assertThat(result).isTrue();
        }

        @Test
        void isSameUsernameThanAuthenticatedUser_shouldNotBeCaseSensitive() {
            String givenUsername = tokenUsername.toUpperCase();
            Mockito.when(accessToken.getPreferredUsername()).thenReturn(tokenUsername);

            SecurityContextHolder.setContext(securityContext);

            boolean result = objectToTest.isSameUsernameThanAuthenticatedUser(givenUsername);

            Assertions.assertThat(result).isTrue();
        }
    }
}
