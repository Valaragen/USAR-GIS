package com.usargis.usargisapi.web.controller.componentTests;

import com.usargis.usargisapi.config.SpringKeycloakSecurityConfiguration;
import com.usargis.usargisapi.service.contract.AvailabilityService;
import com.usargis.usargisapi.service.contract.ModelMapperService;
import com.usargis.usargisapi.service.contract.SecurityService;
import com.usargis.usargisapi.util.Constant;
import com.usargis.usargisapi.util.ErrorConstant;
import com.usargis.usargisapi.util.keycloakAuthMock.WithMockKeycloakUser;
import com.usargis.usargisapi.web.controller.AvailabilityController;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@WebMvcTest({AvailabilityController.class, SecurityService.class})
@Import(SpringKeycloakSecurityConfiguration.class)
class AvailabilityCompTest {

    private final String API_V1_PATH = Constant.API_PATH + Constant.V1_PATH;

    private final String authenticatedUserUsername = "a_user";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AvailabilityService availabilityService;
    @MockBean
    private ModelMapperService modelMapperService;
    @Autowired
    private SecurityService securityService;

    @Test
    @WithMockKeycloakUser(roles = "LEADER")
    void searchForAvailabilities_shouldBeAccessibleToLeader() throws Exception {
        mockMvc.perform(get(API_V1_PATH + Constant.AVAILABILITIES_PATH))

                .andExpect(status().isNotFound())
                .andExpect(jsonPath("message", Matchers.containsString(ErrorConstant.NO_AVAILABILITY_FOUND)));
    }

    @Test
    @WithMockKeycloakUser(roles = "MEMBER")
    void searchForAvailabilities_UserIsMember_Forbidden() throws Exception {
        mockMvc.perform(get(API_V1_PATH + Constant.AVAILABILITIES_PATH))

                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockKeycloakUser(username = authenticatedUserUsername)
    void searchForAvailabilities_authenticatedUserUsernameIsSameThanUsernameInQueryString_Forbidden() throws Exception {
        mockMvc.perform(get(API_V1_PATH + Constant.AVAILABILITIES_PATH + "?userUsername=" + authenticatedUserUsername))

                .andExpect(status().isNotFound());
    }
}
