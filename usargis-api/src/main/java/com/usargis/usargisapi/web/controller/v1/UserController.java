package com.usargis.usargisapi.web.controller.v1;

import com.usargis.usargisapi.model.UserInfo;
import com.usargis.usargisapi.service.contract.UserInfoService;
import com.usargis.usargisapi.util.Constant;
import com.usargis.usargisapi.util.ErrorConstant;
import com.usargis.usargisapi.web.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(Constant.V1_PATH)
public class UserController {

    private UserInfoService userInfoService;

    @Autowired
    public UserController(UserInfoService userInfoService) {
        this.userInfoService = userInfoService;
    }

    @GetMapping(Constant.USERS)
    public ResponseEntity<List<UserInfo>> findAllUsers() {
        List<UserInfo> userInfos = userInfoService.findAll();
        if (userInfos.isEmpty()) {
            throw new NotFoundException(ErrorConstant.NO_USERS_FOUND);
        }
        return new ResponseEntity<>(userInfos, HttpStatus.FOUND);
    }

    @GetMapping(Constant.USERS + Constant.SLASH_ID_PATH)
    public ResponseEntity<UserInfo> getUserById(@PathVariable String id) {
        Optional<UserInfo> userInfo = userInfoService.findById(id);
        UserInfo result = userInfo.orElseThrow(() -> new NotFoundException(MessageFormat.format(ErrorConstant.NO_USER_FOUND_FOR_ID, id)));
        return new ResponseEntity<>(result, HttpStatus.FOUND);
    }

    @PostMapping(Constant.USERS)
    public ResponseEntity<UserInfo> createNewUser(@RequestBody UserInfo userInfo) {
        //TODO implement
        UserInfo result = userInfoService.save(userInfo);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @DeleteMapping(Constant.USERS + Constant.SLASH_ID_PATH)
    public ResponseEntity<UserInfo> deleteUser(@PathVariable String id) {
        userInfoService.delete(userInfoService.findById(id).orElseThrow(() -> new NotFoundException(MessageFormat.format(ErrorConstant.NO_USER_FOUND_FOR_ID, id))));
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
