package com.usargis.usargisapi.security;

import com.usargis.usargisapi.config.SpringKeycloakSecurityConfiguration;
import com.usargis.usargisapi.security.testController.KeycloakSecurityTestController;
import com.usargis.usargisapi.service.contract.SecurityService;
import com.usargis.usargisapi.service.contract.UserInfoService;
import com.usargis.usargisapi.testutils.keycloakAuthMock.WithMockKeycloakUser;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.core.StringContains.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@WebMvcTest(value = {KeycloakSecurityTestController.class, SecurityService.class})
@Import(SpringKeycloakSecurityConfiguration.class)
class KeycloakSecurityWebLayerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserInfoService userInfoService;

    @Autowired
    private SecurityService securityService;


    @Test
    @WithMockKeycloakUser(roles = "LEADER")
    void preAuthorizeLeader_shouldAllowUserWithLeaderRole() throws Exception {
        this.mockMvc.perform(get("/keycloak-test/leader")).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Authorized")));
    }

    @Test
    @WithMockKeycloakUser
    void preAuthorizeHasRoleLeader_shouldNotAllowWithoutLeaderRole() throws Exception {
        this.mockMvc.perform(get("/keycloak-test/leader")).andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockKeycloakUser(username = "user")
    void hasAccessTest() throws Exception {
        this.mockMvc.perform(get("/keycloak-test/sameuser/user")).andDo(print())
                .andExpect(status().isOk());
    }

}


//@Slf4j
//@ActiveProfiles("test")
//@WebMvcTest(KeycloakSecurityTestController.class)
//@Import(SpringKeycloakSecurityConfiguration.class)
//class KeycloakSecurityWebLayerTestApplicationContext {
//
//    @Test
//    @WithMockKeycloakUser(roles = "LEADER")
//    void applicationContextMockKeycloakUserTest() {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        Assertions.assertThat(authentication).isNotNull();
//
//        Collection<? extends GrantedAuthority> result = authentication.getAuthorities();
//        Assertions.assertThat(result).isNotEmpty();
//    }
//
//    @Test
//    @WithMockUser(roles = "LEADER")
//    void applicationContextMockUserTest() {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        Assertions.assertThat(authentication).isNotNull();
//
//        Collection<? extends GrantedAuthority> result = authentication.getAuthorities();
//        Assertions.assertThat(result).isNotEmpty();
//
//    }
//
//    @Test
//    void applicationContextTest() {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        Assertions.assertThat(authentication).isNull();
//    }
//}
