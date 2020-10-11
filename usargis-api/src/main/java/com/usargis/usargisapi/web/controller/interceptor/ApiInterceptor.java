package com.usargis.usargisapi.web.controller.interceptor;

import com.usargis.usargisapi.core.model.UserInfo;
import com.usargis.usargisapi.service.contract.ModelMapperService;
import com.usargis.usargisapi.service.contract.SecurityService;
import com.usargis.usargisapi.service.contract.UserInfoService;
import com.usargis.usargisapi.util.ErrorConstant;
import com.usargis.usargisapi.web.exception.AccessForbiddenException;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.representations.AccessToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

//TODO get rid of this and use event bus
@Component
public class ApiInterceptor implements HandlerInterceptor {
    @Autowired
    private SecurityService securityService;
    @Autowired
    private UserInfoService userInfoService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        AccessToken accessToken = securityService.getKeycloakAccessToken();
        UserInfo userInfoFromToken = UserInfo.builder()
                .id(accessToken.getId())
                .username(accessToken.getPreferredUsername())
                .email(accessToken.getEmail())
                .firstName(accessToken.getGivenName())
                .lastName(accessToken.getFamilyName())
                .email(accessToken.getEmail())
                .emailVerified(accessToken.getEmailVerified())
                .phone(accessToken.getPhoneNumber())
                .groups(null)
                .inTeams(null).build();

        Optional<UserInfo> userInfoInDb = userInfoService.findByUsername(accessToken.getPreferredUsername());
        if(!userInfoInDb.isPresent()) {
            userInfoService.save(userInfoFromToken);
        }

        return true;
    }
}
