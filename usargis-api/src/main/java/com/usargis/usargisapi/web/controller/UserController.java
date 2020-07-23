package com.usargis.usargisapi.web.controller;

import com.usargis.usargisapi.core.dto.UserInfoDto;
import com.usargis.usargisapi.core.model.UserInfo;
import com.usargis.usargisapi.service.contract.ModelMapperService;
import com.usargis.usargisapi.service.contract.UserInfoService;
import com.usargis.usargisapi.util.Constant;
import com.usargis.usargisapi.util.ErrorConstant;
import com.usargis.usargisapi.web.exception.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RestController
public class UserController implements ApiRestController {

    private UserInfoService userInfoService;
    private ModelMapperService modelMapperService;

    @Autowired
    public UserController(UserInfoService userInfoService, ModelMapperService modelMapperService) {
        this.userInfoService = userInfoService;
        this.modelMapperService = modelMapperService;
    }

    @GetMapping(Constant.USERS_PATH)
    public ResponseEntity<List<UserInfoDto.Response>> findAllUserInfos() {
        List<UserInfo> userInfos = userInfoService.findAll();
        if (userInfos.isEmpty()) {
            throw new NotFoundException(ErrorConstant.NO_USER_FOUND);
        }
        return new ResponseEntity<>(userInfos.stream().map(this::convertToResponseDto).collect(Collectors.toList()), HttpStatus.OK);
    }

    @GetMapping(Constant.USERS_PATH + Constant.SLASH_ID_PATH)
    public ResponseEntity<UserInfoDto.Response> getUserInfoById(@PathVariable String id) {
        Optional<UserInfo> userInfoOptional = userInfoService.findById(id);
        UserInfo userInfo = userInfoOptional.orElseThrow(() -> new NotFoundException(MessageFormat.format(ErrorConstant.NO_USER_FOUND_FOR_USERNAME, id)));
        return new ResponseEntity<>(convertToResponseDto(userInfo), HttpStatus.OK);
    }

    @PostMapping(Constant.USERS_PATH)
    public ResponseEntity<UserInfoDto.Response> createNewUserInfo(@RequestBody UserInfoDto.PostRequest userInfoCreateDto) {
        UserInfo userInfo = userInfoService.create(userInfoCreateDto);
        return new ResponseEntity<>(convertToResponseDto(userInfo), HttpStatus.CREATED);
    }

    @PutMapping(Constant.USERS_PATH + Constant.SLASH_ID_PATH)
    public ResponseEntity<UserInfoDto.Response> updateUserInfo(@PathVariable String id, @RequestBody UserInfoDto.PostRequest updateDto) {
        UserInfo userInfo = userInfoService.update(id, updateDto);
        return new ResponseEntity<>(convertToResponseDto(userInfo), HttpStatus.OK);
    }

    @DeleteMapping(Constant.USERS_PATH + Constant.SLASH_ID_PATH)
    public ResponseEntity deleteUserInfo(@PathVariable String id) {
        userInfoService.delete(userInfoService.findById(id).orElseThrow(() -> new NotFoundException(MessageFormat.format(ErrorConstant.NO_USER_FOUND_FOR_USERNAME, id))));
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    private UserInfoDto.Response convertToResponseDto(UserInfo userInfo) {
        return modelMapperService.map(userInfo, UserInfoDto.Response.class);
    }

}
