package com.usargis.usargisapi;

import com.usargis.usargisapi.config.SpringKeycloakSecurityConfiguration;
import com.usargis.usargisapi.controller.TestController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = TestController.class)
@Import(SpringKeycloakSecurityTestConfiguration.class)
// Disable Keycloak configuration processing
@TestPropertySource(properties = {"keycloak.enabled=false"})
class SpringKeycloakSecurityTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    void testUnsecuredPathIsAllowedForAll() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/unsecured"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void testAdminPathIsNotAllowedForAll() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/admin"))
                .andExpect(status().isForbidden());
    }
    @Test
    @WithMockUser(roles = "ADMIN")
    void testAdmindPathIsOnlyAllowedForAdminProfil() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/admin"))
                .andExpect(status().isOk());
    }
    @Test
    @WithMockUser(roles = "MEMBER")
    void testUserPathIsOnlyAllowedForUserProfil() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/member"))
                .andExpect(status().isOk());
    }
    @Test
    @WithMockUser(roles = "MEMBER")
    void testAdmindPathIsNotAllowedForUserProfil() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/admin"))
                .andExpect(status().isForbidden());
    }
}

@TestConfiguration
@EnableWebSecurity
class SpringKeycloakSecurityTestConfiguration extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // use the common configuration to validate matchers
        http.apply(new SpringKeycloakSecurityConfiguration.CommonSpringKeycloakSecurityAdapter());

    }
}