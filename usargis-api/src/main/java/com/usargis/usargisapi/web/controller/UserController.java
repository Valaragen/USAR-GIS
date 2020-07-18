package com.usargis.usargisapi.web.controller;

import com.usargis.usargisapi.core.model.UserInfo;
import com.usargis.usargisapi.service.contract.UserInfoService;
import com.usargis.usargisapi.util.Constant;
import com.usargis.usargisapi.util.ErrorConstant;
import com.usargis.usargisapi.web.exception.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;

@PreAuthorize("hasRole('" + Constant.ADMIN_ROLE + "')")
@Slf4j
@RestController
public class UserController {

    private UserInfoService userInfoService;

    @Autowired
    public UserController(UserInfoService userInfoService) {
        this.userInfoService = userInfoService;
    }

    @PreAuthorize("hasRole('" + Constant.LEADER_ROLE + "')")
    @GetMapping(Constant.USERS)
    public ResponseEntity<List<UserInfo>> findAllUsers() {
        List<UserInfo> userInfos = userInfoService.findAll();
        if (userInfos.isEmpty()) {
            throw new NotFoundException(ErrorConstant.NO_USERS_FOUND);
        }
        return new ResponseEntity<>(userInfos, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('" + Constant.LEADER_ROLE + "') or @securityServiceImpl.isSameUsernameThanAuthenticatedUser(#string)")
    @GetMapping(Constant.USERS + Constant.SLASH_STRING_PATH)
    public ResponseEntity<UserInfo> getUserByUsername(@PathVariable String string) {
        Optional<UserInfo> userInfo = userInfoService.findByUsername(string);
        UserInfo result = userInfo.orElseThrow(() -> new NotFoundException(MessageFormat.format(ErrorConstant.NO_USER_FOUND_FOR_ID, string)));
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PreAuthorize("denyAll()")
    @PostMapping(Constant.USERS)
    public ResponseEntity<UserInfo> createNewUser(@RequestBody UserInfo userInfo) {
        UserInfo result = userInfoService.save(userInfo);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @PreAuthorize("denyAll()")
    @DeleteMapping(Constant.USERS + Constant.SLASH_ID_PATH)
    public ResponseEntity<UserInfo> deleteUser(@PathVariable String id) {
        userInfoService.delete(userInfoService.findById(id).orElseThrow(() -> new NotFoundException(MessageFormat.format(ErrorConstant.NO_USER_FOUND_FOR_ID, id))));
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
