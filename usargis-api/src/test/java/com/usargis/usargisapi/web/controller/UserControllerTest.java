package com.usargis.usargisapi.web.controller;

import com.usargis.usargisapi.core.dto.UserInfoDto;
import com.usargis.usargisapi.core.model.UserInfo;
import com.usargis.usargisapi.service.contract.ModelMapperService;
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
            Mockito.when(modelMapperService.map(Mockito.any(UserInfo.class), Mockito.any())).thenReturn(new UserInfoDto.Response());

            objectToTest.findAllUserInfos();

            Mockito.verify(modelMapperService, Mockito.times(userInfosFound.size())).map(Mockito.any(UserInfo.class), Mockito.any());
        }

        @Test
        void findAllUserInfos_userInfoFound_returnStatusOkWithListOfUserInfosResponseDto() {
            Mockito.when(userInfoService.findAll()).thenReturn(userInfosFound);
            Mockito.when(modelMapperService.map(Mockito.any(UserInfo.class), Mockito.any())).thenReturn(new UserInfoDto.Response());

            ResponseEntity<List<UserInfoDto.Response>> result = objectToTest.findAllUserInfos();

            Assertions.assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
            Assertions.assertThat(Objects.requireNonNull(result.getBody()).size()).isEqualTo(userInfosFound.size());
        }
    }

    @Nested
    class getUserInfoByIdTest {
        private final String userInfoIdToFind = "id";
        private final UserInfo userInfoFound = new UserInfo();
        private final UserInfoDto.Response userInfoResponseDto = new UserInfoDto.Response();

        @Test
        void getUserInfoById_shouldCallServiceLayer() {
            Mockito.when(userInfoService.findById(userInfoIdToFind)).thenReturn(Optional.of(userInfoFound));

            objectToTest.getUserInfoById(userInfoIdToFind);

            Mockito.verify(userInfoService).findById(userInfoIdToFind);
        }

        @Test
        void getUserInfoById_noUserInfoFound_throwNotFoundException() {
            Mockito.when(userInfoService.findById(userInfoIdToFind)).thenReturn(Optional.empty());

            Assertions.assertThatThrownBy(() -> objectToTest.getUserInfoById(userInfoIdToFind))
                    .isInstanceOf(NotFoundException.class)
                    .hasMessage(MessageFormat.format(ErrorConstant.NO_USER_FOUND_FOR_ID, userInfoIdToFind));
        }

        @Test
        void getUserInfoById_shouldConvertUserInfoToResponseDto() {
            Mockito.when(userInfoService.findById(userInfoIdToFind)).thenReturn(Optional.of(userInfoFound));
            Mockito.when(modelMapperService.map(userInfoFound, UserInfoDto.Response.class)).thenReturn(userInfoResponseDto);

            objectToTest.getUserInfoById(userInfoIdToFind);

            Mockito.verify(modelMapperService).map(userInfoFound, UserInfoDto.Response.class);
        }

        @Test
        void getUserInfoById_userInfoFound_returnStatusOkAndUserInfoResponseDto() {
            Mockito.when(userInfoService.findById(userInfoIdToFind)).thenReturn(Optional.of(userInfoFound));
            Mockito.when(modelMapperService.map(userInfoFound, UserInfoDto.Response.class)).thenReturn(userInfoResponseDto);

            ResponseEntity<UserInfoDto.Response> result = objectToTest.getUserInfoById(userInfoIdToFind);

            Assertions.assertThat(result.getStatusCode())
                    .isEqualTo(HttpStatus.OK);
            Assertions.assertThat(result.getBody())
                    .isEqualTo(userInfoResponseDto);
            Assertions.assertThat(result.getBody()).isInstanceOf(UserInfoDto.Response.class);
        }
    }

    @Nested
    class createNewUserInfoTest {
        private final UserInfoDto.PostRequest userInfoToSave = UserInfoDtoMother.postRequestSample().build();
        private final UserInfo newUserInfo = new UserInfo();
        private final UserInfoDto.Response userInfoResponseDto = new UserInfoDto.Response();

        @Test
        void createNewUserInfo_shouldCallServiceLayer() {
            Mockito.when(userInfoService.create(userInfoToSave)).thenReturn(newUserInfo);

            objectToTest.createNewUserInfo(userInfoToSave);

            Mockito.verify(userInfoService).create(userInfoToSave);
        }

        @Test
        void createNewUserInfo_shouldConvertUserInfoToResponseDto() {
            Mockito.when(userInfoService.create(userInfoToSave)).thenReturn(newUserInfo);
            Mockito.when(modelMapperService.map(newUserInfo, UserInfoDto.Response.class)).thenReturn(userInfoResponseDto);

            objectToTest.createNewUserInfo(userInfoToSave);

            Mockito.verify(modelMapperService).map(newUserInfo, UserInfoDto.Response.class);
        }

        @Test
        void createNewUserInfo_userInfoCreated_returnStatusCreatedAndUserInfoResponseDto() {
            Mockito.when(userInfoService.create(userInfoToSave)).thenReturn(newUserInfo);
            Mockito.when(modelMapperService.map(newUserInfo, UserInfoDto.Response.class)).thenReturn(userInfoResponseDto);

            ResponseEntity<UserInfoDto.Response> result = objectToTest.createNewUserInfo(userInfoToSave);

            Assertions.assertThat(result.getStatusCode())
                    .isEqualTo(HttpStatus.CREATED);
            Assertions.assertThat(result.getBody())
                    .isEqualTo(userInfoResponseDto);
            Assertions.assertThat(result.getBody()).isInstanceOf(UserInfoDto.Response.class);
        }
    }

    @Nested
    class updateUserInfoTest {
        private final String userInfoId = "id";
        private final UserInfoDto.PostRequest userInfoToUpdate = UserInfoDtoMother.postRequestSample().build();
        private final UserInfo updateUserInfo = new UserInfo();
        private final UserInfoDto.Response userInfoResponseDto = new UserInfoDto.Response();

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
            Mockito.when(modelMapperService.map(updateUserInfo, UserInfoDto.Response.class))
                    .thenReturn(userInfoResponseDto);

            objectToTest.updateUserInfo(userInfoId, userInfoToUpdate);

            Mockito.verify(modelMapperService).map(updateUserInfo, UserInfoDto.Response.class);
        }

        @Test
        void updateUserInfoTest_userInfoCreated_returnStatusOkAndUserInfoResponseDto() {
            Mockito.when(userInfoService.update(userInfoId, userInfoToUpdate))
                    .thenReturn(updateUserInfo);
            Mockito.when(modelMapperService.map(updateUserInfo, UserInfoDto.Response.class))
                    .thenReturn(userInfoResponseDto);

            ResponseEntity<UserInfoDto.Response> result =
                    objectToTest.updateUserInfo(userInfoId, userInfoToUpdate);

            Assertions.assertThat(result.getStatusCode())
                    .isEqualTo(HttpStatus.OK);
            Assertions.assertThat(result.getBody())
                    .isEqualTo(userInfoResponseDto);
            Assertions.assertThat(result.getBody()).isInstanceOf(UserInfoDto.Response.class);
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
