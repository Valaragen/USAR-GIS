package com.usargis.usargisapi.service.impl;

import com.usargis.usargisapi.model.UserInfo;
import com.usargis.usargisapi.repository.UserInfoRepository;
import com.usargis.usargisapi.service.contract.UserInfoService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserInfoServiceImpl implements UserInfoService {

    private UserInfoRepository userInfoRepository;

    public UserInfoServiceImpl(UserInfoRepository userInfoRepository) {
        this.userInfoRepository = userInfoRepository;
    }

    @Override
    public Optional<UserInfo> findById(String id) {
        return userInfoRepository.findById(id);
    }

    @Override
    public UserInfo save(UserInfo userInfo) {
        return userInfoRepository.save(userInfo);
    }

    @Override
    public void delete(UserInfo userInfo) {
        userInfoRepository.delete(userInfo);
    }
}
