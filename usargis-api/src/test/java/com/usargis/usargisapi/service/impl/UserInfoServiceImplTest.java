package com.usargis.usargisapi.service.impl;

import com.usargis.usargisapi.core.model.UserInfo;
import com.usargis.usargisapi.repository.UserInfoRepository;
import com.usargis.usargisapi.service.contract.UserInfoService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

class UserInfoServiceImplTest {

    private UserInfoService objectToTest;

    private UserInfoRepository userInfoRepository = Mockito.mock(UserInfoRepository.class);

    @BeforeEach
    void setup() {
        objectToTest = new UserInfoServiceImpl(userInfoRepository);
    }


    @Test
    void findAll_shouldCallRepositoryAndReturnList() {
        List<UserInfo> returnedUserList = Collections.singletonList(new UserInfo());
        Mockito.when(userInfoRepository.findAll()).thenReturn(returnedUserList);

        List<UserInfo> result = objectToTest.findAll();

        Assertions.assertThat(result).isEqualTo(returnedUserList);
        Mockito.verify(userInfoRepository).findAll();
    }


    @Test
    void findById_shouldCallRepositoryAndReturnOptional() {
        UserInfo userFound = new UserInfo();
        Optional<UserInfo> expectedResult = Optional.of(userFound);
        String userUUIDToFind = "user";
        Mockito.when(userInfoRepository.findById(userUUIDToFind)).thenReturn(expectedResult);

        Optional<UserInfo> result = objectToTest.findById(userUUIDToFind);

        Assertions.assertThat(result).isEqualTo(expectedResult);
        Mockito.verify(userInfoRepository).findById(userUUIDToFind);
    }

    @Test
    void save_shouldCallRepositoryAndReturnUser() {
        UserInfo userToSave = new UserInfo();
        Mockito.when(userInfoRepository.save(userToSave)).thenReturn(userToSave);

        UserInfo result = objectToTest.save(userToSave);

        Assertions.assertThat(result).isEqualTo(userToSave);
        Mockito.verify(userInfoRepository).save(userToSave);
    }

    @Test
    void delete_shouldCallRepository() {
        UserInfo userToDelete = new UserInfo();
        Mockito.doNothing().when(userInfoRepository).delete(userToDelete);

        objectToTest.delete(userToDelete);

        Mockito.verify(userInfoRepository).delete(userToDelete);
    }

    @Test
    void findByUsername_shouldCallRepositoryAndReturnOptional() {
        UserInfo userFound = new UserInfo();
        Optional<UserInfo> expectedResult = Optional.of(userFound);
        String userUsernameToFind = "user";
        Mockito.when(userInfoRepository.findByUsername(userUsernameToFind)).thenReturn(expectedResult);

        Optional<UserInfo> result = objectToTest.findByUsername(userUsernameToFind);

        Assertions.assertThat(result).isEqualTo(expectedResult);
        Mockito.verify(userInfoRepository).findByUsername(userUsernameToFind);
    }

}
