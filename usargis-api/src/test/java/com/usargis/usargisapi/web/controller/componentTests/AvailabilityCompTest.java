package com.usargis.usargisapi.web.controller.componentTests;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.usargis.usargisapi.config.SpringKeycloakSecurityConfiguration;
import com.usargis.usargisapi.core.dto.AvailabilityDto;
import com.usargis.usargisapi.service.contract.AvailabilityService;
import com.usargis.usargisapi.service.contract.ModelMapperService;
import com.usargis.usargisapi.service.contract.SecurityService;
import com.usargis.usargisapi.util.Constant;
import com.usargis.usargisapi.util.keycloakAuthMock.WithMockKeycloakUser;
import com.usargis.usargisapi.util.objectMother.dto.AvailabilityDtoMother;
import com.usargis.usargisapi.web.controller.AvailabilityController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import javax.ws.rs.core.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@WebMvcTest({AvailabilityController.class, SecurityService.class, AvailabilityService.class})
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
    @Autowired
    private ObjectMapper objectMapper;

    @Nested
    class searchForAvailabilitiesTest {

        private String userUsernameParam = "userUsername";

        @Test
        @WithMockKeycloakUser(roles = "LEADER")
        void searchForAvailabilities_shouldBeAccessibleToLeader() throws Exception {
            mockMvc.perform(get(API_V1_PATH + Constant.AVAILABILITIES_PATH))

                    .andExpect(status().isNotFound());
        }

        @Test
        @WithMockKeycloakUser(roles = "MEMBER")
        void searchForAvailabilities_userIsNotLeader_forbidden() throws Exception {
            mockMvc.perform(get(API_V1_PATH + Constant.AVAILABILITIES_PATH))

                    .andExpect(status().isForbidden());
        }

        @Test
        @WithMockKeycloakUser(username = authenticatedUserUsername)
        void searchForAvailabilities_authenticatedUserUsernameIsSameThanUsernameInQueryParam_authorized() throws Exception {
            mockMvc.perform(get(API_V1_PATH + Constant.AVAILABILITIES_PATH)
                    .param(userUsernameParam, authenticatedUserUsername)).andDo(print())

                    .andExpect(status().isNotFound());
        }

        @Test
        @WithMockKeycloakUser(username = authenticatedUserUsername)
        void searchForAvailabilities_authenticatedUserUsernameIsDifferentThanUsernameInQueryParam_forbidden() throws Exception {
            mockMvc.perform(get(API_V1_PATH + Constant.AVAILABILITIES_PATH)
                    .param(userUsernameParam, "user")).andDo(print())

                    .andExpect(status().isForbidden());
        }
    }

    @Nested
    class getAvailabilityByIdTest {
        private Long idToFind = 1L;

        @BeforeEach
        void setup() {
            Mockito.when(availabilityService.isAvailabilityOwner(idToFind)).thenReturn(false);
        }

        @Test
        @WithMockKeycloakUser(roles = "LEADER")
        void getAvailabilityById_shouldBeAccessibleToLeader() throws Exception {
            mockMvc.perform(get(API_V1_PATH + Constant.AVAILABILITIES_PATH + Constant.SLASH_ID_PATH, idToFind))
                    .andDo(print())

                    .andExpect(status().isNotFound());
        }

        @Test
        @WithMockKeycloakUser(roles = "MEMBER")
        void getAvailabilityById__userIsNotLeader_forbidden() throws Exception {
            mockMvc.perform(get(API_V1_PATH + Constant.AVAILABILITIES_PATH + Constant.SLASH_ID_PATH, idToFind))
                    .andDo(print())

                    .andExpect(status().isForbidden());
        }

        @Test
        @WithMockKeycloakUser(roles = "MEMBER")
        void getAvailabilityById_userIsAvailabilityOwner_authorized() throws Exception {
            Mockito.when(availabilityService.isAvailabilityOwner(idToFind)).thenReturn(true);

            mockMvc.perform(get(API_V1_PATH + Constant.AVAILABILITIES_PATH + Constant.SLASH_ID_PATH, idToFind))
                    .andDo(print())

                    .andExpect(status().isNotFound());
        }
    }

    @Nested
    class createNewAvailabilityTest {
        AvailabilityDto.AvailabilityCreate availabilityCreate = AvailabilityDtoMother.createSample().build();

        @Test
        @WithMockKeycloakUser(roles = "ADMIN")
        void createNewAvailability_shouldBeAccessibleToAdmin() throws Exception {
            String json = objectMapper.writeValueAsString(availabilityCreate);

            mockMvc.perform(post(API_V1_PATH + Constant.AVAILABILITIES_PATH)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(json)).andDo(print())

                    .andExpect(status().isCreated());
        }

        @Test
        @WithMockKeycloakUser(roles = {"LEADER", "MEMBER"})
        void createNewAvailability_userIsNotAdmin_forbidden() throws Exception {
            String json = objectMapper.writeValueAsString(availabilityCreate);

            mockMvc.perform(post(API_V1_PATH + Constant.AVAILABILITIES_PATH)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(json)).andDo(print())

                    .andExpect(status().isForbidden());
        }

        @Test
        @WithMockKeycloakUser(username = authenticatedUserUsername)
        void createNewAvailability_authenticatedUserUsernameIsSameThanUsernameInDto_authorized() throws Exception {
            availabilityCreate.setUserInfoUsername(authenticatedUserUsername);
            String json = objectMapper.writeValueAsString(availabilityCreate);

            mockMvc.perform(post(API_V1_PATH + Constant.AVAILABILITIES_PATH)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(json)).andDo(print())

                    .andExpect(status().isCreated());
        }

        @Test
        @WithMockKeycloakUser(username = authenticatedUserUsername)
        void createNewAvailability_authenticatedUserUsernameIsDifferentThanUsernameInDto_forbidden() throws Exception {
            String json = objectMapper.writeValueAsString(availabilityCreate);

            mockMvc.perform(post(API_V1_PATH + Constant.AVAILABILITIES_PATH)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(json)).andDo(print())

                    .andExpect(status().isForbidden());
        }
    }

    @Nested
    class updateAvailabilityTest {
        private final Long idToUpdate = 1L;
        AvailabilityDto.AvailabilityUpdate availabilityUpdate = AvailabilityDtoMother.updateSample().build();

        @BeforeEach
        void setup() {
            Mockito.when(availabilityService.isAvailabilityOwner(idToUpdate)).thenReturn(false);
        }

        @Test
        @WithMockKeycloakUser(roles = "ADMIN")
        void updateAvailability_shouldBeAccessibleToAdmin() throws Exception {
            String json = objectMapper.writeValueAsString(availabilityUpdate);

            mockMvc.perform(put(API_V1_PATH + Constant.AVAILABILITIES_PATH + Constant.SLASH_ID_PATH, idToUpdate)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(json)).andDo(print())

                    .andExpect(status().isOk());
        }

        @Test
        @WithMockKeycloakUser(roles = {"LEADER", "MEMBER"})
        void updateAvailability_userIsNotAdmin_forbidden() throws Exception {
            String json = objectMapper.writeValueAsString(availabilityUpdate);

            mockMvc.perform(put(API_V1_PATH + Constant.AVAILABILITIES_PATH + Constant.SLASH_ID_PATH, idToUpdate)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(json)).andDo(print())

                    .andExpect(status().isForbidden());
        }

        @Test
        @WithMockKeycloakUser(username = authenticatedUserUsername)
        void updateAvailability__userIsAvailabilityOwner_authorized() throws Exception {
            Mockito.when(availabilityService.isAvailabilityOwner(idToUpdate)).thenReturn(true);
            String json = objectMapper.writeValueAsString(availabilityUpdate);

            mockMvc.perform(put(API_V1_PATH + Constant.AVAILABILITIES_PATH + Constant.SLASH_ID_PATH, idToUpdate)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(json)).andDo(print())

                    .andExpect(status().isOk());
        }
    }

    @Nested
    class deleteAvailabilityTest {
        private Long idToDelete = 1L;

        @BeforeEach
        void setup() {
            Mockito.when(availabilityService.isAvailabilityOwner(idToDelete)).thenReturn(false);
        }

        @Test
        @WithMockKeycloakUser(roles = "LEADER")
        void deleteAvailability_shouldBeAccessibleToLeader() throws Exception {
            mockMvc.perform(get(API_V1_PATH + Constant.AVAILABILITIES_PATH + Constant.SLASH_ID_PATH, idToDelete))
                    .andDo(print())

                    .andExpect(status().isNotFound());
        }

        @Test
        @WithMockKeycloakUser(roles = "MEMBER")
        void deleteAvailability__userIsNotLeader_forbidden() throws Exception {
            mockMvc.perform(get(API_V1_PATH + Constant.AVAILABILITIES_PATH + Constant.SLASH_ID_PATH, idToDelete))
                    .andDo(print())

                    .andExpect(status().isForbidden());
        }

        @Test
        @WithMockKeycloakUser(roles = "MEMBER")
        void deleteAvailability_userIsAvailabilityOwner_authorized() throws Exception {
            Mockito.when(availabilityService.isAvailabilityOwner(idToDelete)).thenReturn(true);

            mockMvc.perform(get(API_V1_PATH + Constant.AVAILABILITIES_PATH + Constant.SLASH_ID_PATH, idToDelete))
                    .andDo(print())

                    .andExpect(status().isNotFound());
        }
    }
}
