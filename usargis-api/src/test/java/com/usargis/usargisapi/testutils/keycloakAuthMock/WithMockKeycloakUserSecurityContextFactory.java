package com.usargis.usargisapi.testutils.keycloakAuthMock;

import lombok.extern.slf4j.Slf4j;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.adapters.RefreshableKeycloakSecurityContext;
import org.keycloak.adapters.spi.KeycloakAccount;
import org.keycloak.adapters.springsecurity.account.SimpleKeycloakAccount;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.keycloak.representations.AccessToken;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;
import org.springframework.util.StringUtils;

import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Slf4j
public class WithMockKeycloakUserSecurityContextFactory implements WithSecurityContextFactory<WithMockKeycloakUser> {

    @Value("${keycloak.resource}")
    private String defaultKeycloakResource;

    public SecurityContext createSecurityContext(WithMockKeycloakUser keycloakMockedUser) {
        String username = StringUtils.hasLength(keycloakMockedUser.username()) ? keycloakMockedUser
                .username() : keycloakMockedUser.value();
        if (username == null) {
            throw new IllegalArgumentException(keycloakMockedUser
                    + " cannot have null username on both username and value properites");
        }

        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        String[] roles = keycloakMockedUser.roles();
        int roleNbr = roles.length;

        String role;
        for (int i = 0; i < roleNbr; i++) {
            role = roles[i];
            if (role.startsWith("ROLE_")) {
                throw new IllegalArgumentException("roles cannot start with ROLE_ Got " + role);
            }

            grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_" + role));
        }

        String resource = StringUtils.hasLength(keycloakMockedUser.resource()) ? keycloakMockedUser
                .resource() : defaultKeycloakResource;

        Map<String, AccessToken.Access> resourceAccess = new HashMap<>();
        Set<String> userRoles = Arrays.stream(keycloakMockedUser.roles()).collect(Collectors.toSet());

        AccessToken keycloakAccessToken = new AccessToken();

        AccessToken.Access access = new AccessToken.Access();
        userRoles.forEach(access::addRole);
        resourceAccess.put(resource, access);
        keycloakAccessToken.setResourceAccess(resourceAccess);

        RefreshableKeycloakSecurityContext securityContext = mock(RefreshableKeycloakSecurityContext.class);
        keycloakAccessToken.setPreferredUsername(username);
        when(securityContext.getToken()).thenReturn(keycloakAccessToken);

        Principal principal = new KeycloakPrincipal<>(username, securityContext);
        KeycloakAccount keycloakAccount = new SimpleKeycloakAccount(principal, userRoles, securityContext);
        Authentication authentication = new KeycloakAuthenticationToken(keycloakAccount, false, grantedAuthorities);

        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authentication);
        return context;
    }
}