package com.usargis.usargisapi.web.controller;

import com.usargis.usargisapi.model.UserInfo;
import com.usargis.usargisapi.service.contract.UserInfoService;
import com.usargis.usargisapi.service.impl.UserInfoServiceImpl;
import com.usargis.usargisapi.util.ErrorConstant;
import com.usargis.usargisapi.web.exception.NotFoundException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

class UserControllerTest {

    private UserController objectToTest;

    private UserInfoService userInfoService = Mockito.mock(UserInfoServiceImpl.class);

    @BeforeEach
    void setup() {
        objectToTest = new UserController(userInfoService);
    }

    @Nested
    class findAllUsersTest {
        @Test
        void findAllUsers_shouldCallServiceLayer() {
            Mockito.when(userInfoService.findAll()).thenReturn(Collections.singletonList(new UserInfo()));

            objectToTest.findAllUsers();

            Mockito.verify(userInfoService).findAll();
        }

        @Test
        void findAllUsers_noUserFound_throwNotFoundException() {
            Mockito.when(userInfoService.findAll()).thenReturn(new ArrayList<>());

            Assertions.assertThatThrownBy(() -> objectToTest.findAllUsers())
                    .isInstanceOf(NotFoundException.class)
                    .hasMessage(ErrorConstant.NO_USERS_FOUND);
        }

        @Test
        void findAllUsers_usersFound_returnResponseEntityWithUserList() {
            List<UserInfo> returnedUserList = Collections.singletonList(new UserInfo());
            Mockito.when(userInfoService.findAll()).thenReturn(returnedUserList);

            ResponseEntity result = objectToTest.findAllUsers();

            Assertions.assertThat(result.getStatusCode())
                    .isEqualTo(HttpStatus.OK);
            Assertions.assertThat(result.getBody())
                    .isEqualTo(returnedUserList);
        }
    }

    @Nested
    class getUserByUsernameTest {
        private final String usernameToFind = "user";
        private final UserInfo userFound = new UserInfo();

        @Test
        void getUserByUsername_shouldCallServiceLayer() {
            Mockito.when(userInfoService.findByUsername(usernameToFind)).thenReturn(Optional.of(userFound));

            objectToTest.getUserByUsername(usernameToFind);

            Mockito.verify(userInfoService).findByUsername(usernameToFind);
        }

        @Test
        void getUserByUsername_noUserFound_throwNotFoundException() {
            Mockito.when(userInfoService.findByUsername(usernameToFind)).thenReturn(Optional.empty());

            Assertions.assertThatThrownBy(() -> objectToTest.getUserByUsername(usernameToFind))
                    .isInstanceOf(NotFoundException.class)
                    .hasMessage(MessageFormat.format(ErrorConstant.NO_USER_FOUND_FOR_ID, usernameToFind));
        }

        @Test
        void getUserByUsername_userFound_returnResponseEntityWithUserAndStatusOk() {
            Mockito.when(userInfoService.findByUsername(usernameToFind)).thenReturn(Optional.of(userFound));

            ResponseEntity result = objectToTest.getUserByUsername(usernameToFind);

            Assertions.assertThat(result.getStatusCode())
                    .isEqualTo(HttpStatus.OK);
            Assertions.assertThat(result.getBody())
                    .isEqualTo(userFound);
        }
    }

    @Nested
    class createNewUserTest {
        private final UserInfo userToSave = new UserInfo();
        private final UserInfo savedUser = new UserInfo();

        @Test
        void createNewUser_shouldCallServiceLayer() {
            Mockito.when(userInfoService.save(userToSave)).thenReturn(savedUser);

            objectToTest.createNewUser(userToSave);

            Mockito.verify(userInfoService).save(userToSave);
        }

        @Test
        void createNewUser_teamCreated_returnResponseEntityWithUserAndStatusCreated() {
            Mockito.when(userInfoService.save(userToSave)).thenReturn(savedUser);

            ResponseEntity result = objectToTest.createNewUser(userToSave);

            Assertions.assertThat(result.getStatusCode())
                    .isEqualTo(HttpStatus.CREATED);
            Assertions.assertThat(result.getBody())
                    .isEqualTo(savedUser);
        }
    }

    @Nested
    class deleteUserTest {
        private final String userToDeleteUUID = "1";
        private final UserInfo foundUserToDelete = new UserInfo();

        @Test
        void deleteUser_shouldCallServiceLayer() {
            Mockito.when(userInfoService.findById(userToDeleteUUID)).thenReturn(Optional.of(foundUserToDelete));
            Mockito.doNothing().when(userInfoService).delete(foundUserToDelete);

            objectToTest.deleteUser(userToDeleteUUID);

            Mockito.verify(userInfoService).delete(foundUserToDelete);
        }

        @Test
        void deleteUser_userDeleted_returnResponseEntityWithStatusNoContent() {
            Mockito.when(userInfoService.findById(userToDeleteUUID)).thenReturn(Optional.of(foundUserToDelete));
            Mockito.doNothing().when(userInfoService).delete(foundUserToDelete);

            ResponseEntity result = objectToTest.deleteUser(userToDeleteUUID);

            Assertions.assertThat(result.getStatusCode())
                    .isEqualTo(HttpStatus.NO_CONTENT);
            Assertions.assertThat(result.getBody())
                    .isEqualTo(null);
        }
    }

}
