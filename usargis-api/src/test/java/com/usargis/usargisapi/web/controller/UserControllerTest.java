package com.usargis.usargisapi.web.controller;

import com.usargis.usargisapi.core.dto.UserInfoDto;
import com.usargis.usargisapi.core.model.UserInfo;
import com.usargis.usargisapi.service.contract.ModelMapperService;
import com.usargis.usargisapi.service.contract.SecurityService;
import com.usargis.usargisapi.service.contract.UserInfoService;
import com.usargis.usargisapi.util.ErrorConstant;
import com.usargis.usargisapi.util.objectMother.dto.UserInfoDtoMother;
import com.usargis.usargisapi.web.exception.NotFoundException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.text.MessageFormat;
import java.util.*;

class UserControllerTest {
    private UserController objectToTest;

    private UserInfoService userInfoService = Mockito.mock(UserInfoService.class);
    private ModelMapperService modelMapperService = Mockito.mock(ModelMapperService.class);

    @BeforeEach
    void setup() {
        objectToTest = new UserController(userInfoService, modelMapperService);
    }

    @Nested
    class findAllUserInfosTest {
        private final List<UserInfo> userInfosFound = Arrays.asList(new UserInfo(), new UserInfo());

        @Test
        void findAllUserInfos_shouldCallServiceLayer() {
            Mockito.when(userInfoService.findAll()).thenReturn(userInfosFound);

            objectToTest.findAllUserInfos();

            Mockito.verify(userInfoService).findAll();
        }

        @Test
        void findAllUserInfos_noUserInfoFound_throwNotFoundException() {
            Mockito.when(userInfoService.findAll()).thenReturn(Collections.emptyList());

            Assertions.assertThatThrownBy(() -> objectToTest.findAllUserInfos())
                    .isInstanceOf(NotFoundException.class)
                    .hasMessage(ErrorConstant.NO_USER_FOUND);
        }

        @Test
        void findAllUserInfos_shouldConvertUserInfosToListOfResponseDto() {
            Mockito.when(userInfoService.findAll()).thenReturn(userInfosFound);
            Mockito.when(modelMapperService.map(Mockito.any(UserInfo.class), Mockito.any())).thenReturn(new UserInfoDto.UserInfoResponse());

            objectToTest.findAllUserInfos();

            Mockito.verify(modelMapperService, Mockito.times(userInfosFound.size())).map(Mockito.any(UserInfo.class), Mockito.any());
        }

        @Test
        void findAllUserInfos_userInfoFound_returnStatusOkWithListOfUserInfosResponseDto() {
            Mockito.when(userInfoService.findAll()).thenReturn(userInfosFound);
            Mockito.when(modelMapperService.map(Mockito.any(UserInfo.class), Mockito.any())).thenReturn(new UserInfoDto.UserInfoResponse());

            ResponseEntity<List<UserInfoDto.UserInfoResponse>> result = objectToTest.findAllUserInfos();

            Assertions.assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
            Assertions.assertThat(Objects.requireNonNull(result.getBody()).size()).isEqualTo(userInfosFound.size());
        }
    }

    @Nested
    class getUserInfoByUsernameTest {
        private final String userInfoUsernameToFind = "username";
        private final UserInfo userInfoFound = new UserInfo();
        private final UserInfoDto.UserInfoResponse userInfoResponseDto = new UserInfoDto.UserInfoResponse();

        @Test
        void getUserInfoByUsername_shouldCallServiceLayer() {
            Mockito.when(userInfoService.findByUsername(userInfoUsernameToFind)).thenReturn(Optional.of(userInfoFound));

            objectToTest.getUserInfoByUsername(userInfoUsernameToFind);

            Mockito.verify(userInfoService).findByUsername(userInfoUsernameToFind);
        }

        @Test
        void getUserInfoByUsername_noUserInfoFound_throwNotFoundException() {
            Mockito.when(userInfoService.findByUsername(userInfoUsernameToFind)).thenReturn(Optional.empty());

            Assertions.assertThatThrownBy(() -> objectToTest.getUserInfoByUsername(userInfoUsernameToFind))
                    .isInstanceOf(NotFoundException.class)
                    .hasMessage(MessageFormat.format(ErrorConstant.NO_USER_FOUND_FOR_USERNAME, userInfoUsernameToFind));
        }

        @Test
        void getUserInfoByUsername_shouldConvertUserInfoToResponseDto() {
            Mockito.when(userInfoService.findByUsername(userInfoUsernameToFind)).thenReturn(Optional.of(userInfoFound));
            Mockito.when(modelMapperService.map(userInfoFound, UserInfoDto.UserInfoResponse.class)).thenReturn(userInfoResponseDto);

            objectToTest.getUserInfoByUsername(userInfoUsernameToFind);

            Mockito.verify(modelMapperService).map(userInfoFound, UserInfoDto.UserInfoResponse.class);
        }

        @Test
        void getUserInfoByUsername_userInfoFound_returnStatusOkAndUserInfoResponseDto() {
            Mockito.when(userInfoService.findByUsername(userInfoUsernameToFind)).thenReturn(Optional.of(userInfoFound));
            Mockito.when(modelMapperService.map(userInfoFound, UserInfoDto.UserInfoResponse.class)).thenReturn(userInfoResponseDto);

            ResponseEntity<UserInfoDto.UserInfoResponse> result = objectToTest.getUserInfoByUsername(userInfoUsernameToFind);

            Assertions.assertThat(result.getStatusCode())
                    .isEqualTo(HttpStatus.OK);
            Assertions.assertThat(result.getBody())
                    .isEqualTo(userInfoResponseDto);
            Assertions.assertThat(result.getBody()).isInstanceOf(UserInfoDto.UserInfoResponse.class);
        }
    }

    @Nested
    class createNewUserInfoTest {
        private final UserInfoDto.UserInfoPostRequest userInfoToSave = UserInfoDtoMother.postRequestSample().build();
        private final UserInfo newUserInfo = new UserInfo();
        private final UserInfoDto.UserInfoResponse userInfoResponseDto = new UserInfoDto.UserInfoResponse();

        @Test
        void createNewUserInfo_shouldCallServiceLayer() {
            Mockito.when(userInfoService.create(userInfoToSave)).thenReturn(newUserInfo);

            objectToTest.createNewUserInfo(userInfoToSave);

            Mockito.verify(userInfoService).create(userInfoToSave);
        }

        @Test
        void createNewUserInfo_shouldConvertUserInfoToResponseDto() {
            Mockito.when(userInfoService.create(userInfoToSave)).thenReturn(newUserInfo);
            Mockito.when(modelMapperService.map(newUserInfo, UserInfoDto.UserInfoResponse.class)).thenReturn(userInfoResponseDto);

            objectToTest.createNewUserInfo(userInfoToSave);

            Mockito.verify(modelMapperService).map(newUserInfo, UserInfoDto.UserInfoResponse.class);
        }

        @Test
        void createNewUserInfo_userInfoCreated_returnStatusCreatedAndUserInfoResponseDto() {
            Mockito.when(userInfoService.create(userInfoToSave)).thenReturn(newUserInfo);
            Mockito.when(modelMapperService.map(newUserInfo, UserInfoDto.UserInfoResponse.class)).thenReturn(userInfoResponseDto);

            ResponseEntity<UserInfoDto.UserInfoResponse> result = objectToTest.createNewUserInfo(userInfoToSave);

            Assertions.assertThat(result.getStatusCode())
                    .isEqualTo(HttpStatus.CREATED);
            Assertions.assertThat(result.getBody())
                    .isEqualTo(userInfoResponseDto);
            Assertions.assertThat(result.getBody()).isInstanceOf(UserInfoDto.UserInfoResponse.class);
        }
    }

    @Nested
    class updateUserInfoTest {
        private final String userInfoId = "id";
        private final UserInfoDto.UserInfoPostRequest userInfoToUpdate = UserInfoDtoMother.postRequestSample().build();
        private final UserInfo updateUserInfo = new UserInfo();
        private final UserInfoDto.UserInfoResponse userInfoResponseDto = new UserInfoDto.UserInfoResponse();

        @Test
        void updateUserInfoTest_shouldCallServiceLayer() {
            Mockito.when(userInfoService.update(userInfoId, userInfoToUpdate)).thenReturn(updateUserInfo);

            objectToTest.updateUserInfo(userInfoId, userInfoToUpdate);

            Mockito.verify(userInfoService).update(userInfoId, userInfoToUpdate);
        }

        @Test
        void updateUserInfoTest_shouldConvertUserInfoToResponseDto() {
            Mockito.when(userInfoService.update(userInfoId, userInfoToUpdate))
                    .thenReturn(updateUserInfo);
            Mockito.when(modelMapperService.map(updateUserInfo, UserInfoDto.UserInfoResponse.class))
                    .thenReturn(userInfoResponseDto);

            objectToTest.updateUserInfo(userInfoId, userInfoToUpdate);

            Mockito.verify(modelMapperService).map(updateUserInfo, UserInfoDto.UserInfoResponse.class);
        }

        @Test
        void updateUserInfoTest_userInfoCreated_returnStatusOkAndUserInfoResponseDto() {
            Mockito.when(userInfoService.update(userInfoId, userInfoToUpdate))
                    .thenReturn(updateUserInfo);
            Mockito.when(modelMapperService.map(updateUserInfo, UserInfoDto.UserInfoResponse.class))
                    .thenReturn(userInfoResponseDto);

            ResponseEntity<UserInfoDto.UserInfoResponse> result =
                    objectToTest.updateUserInfo(userInfoId, userInfoToUpdate);

            Assertions.assertThat(result.getStatusCode())
                    .isEqualTo(HttpStatus.OK);
            Assertions.assertThat(result.getBody())
                    .isEqualTo(userInfoResponseDto);
            Assertions.assertThat(result.getBody()).isInstanceOf(UserInfoDto.UserInfoResponse.class);
        }
    }

    @Nested
    class deleteUserInfoTest {
        private final String userInfoToDeleteId = "id";
        private final UserInfo foundUserInfoToDelete = new UserInfo();

        @Test
        void deleteUserInfo_shouldCallServiceLayer() {
            Mockito.when(userInfoService.findById(userInfoToDeleteId)).thenReturn(Optional.of(foundUserInfoToDelete));
            Mockito.doNothing().when(userInfoService).delete(foundUserInfoToDelete);

            objectToTest.deleteUserInfo(userInfoToDeleteId);

            Mockito.verify(userInfoService).findById(userInfoToDeleteId);
            Mockito.verify(userInfoService).delete(foundUserInfoToDelete);
        }

        @Test
        void deleteUserInfo_userInfoDeleted_returnResponseEntityWithStatusNoContent() {
            Mockito.when(userInfoService.findById(userInfoToDeleteId)).thenReturn(Optional.of(foundUserInfoToDelete));
            Mockito.doNothing().when(userInfoService).delete(foundUserInfoToDelete);

            ResponseEntity result = objectToTest.deleteUserInfo(userInfoToDeleteId);

            Assertions.assertThat(result.getStatusCode())
                    .isEqualTo(HttpStatus.NO_CONTENT);
            Assertions.assertThat(result.getBody())
                    .isNull();
        }
    }
}
