package com.usargis.usargisapi.service.impl;

import com.usargis.usargisapi.core.dto.UserInfoDto;
import com.usargis.usargisapi.core.model.UserInfo;
import com.usargis.usargisapi.repository.UserInfoRepository;
import com.usargis.usargisapi.service.contract.UserInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class UserInfoServiceImpl implements UserInfoService {

    private UserInfoRepository userInfoRepository;

    public UserInfoServiceImpl(UserInfoRepository userInfoRepository) {
        this.userInfoRepository = userInfoRepository;
    }

    @Override
    public List<UserInfo> findAll() {
        return userInfoRepository.findAll();
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

    @Override
    public Optional<UserInfo> findByUsername(String username) {
        return userInfoRepository.findByUsername(username);
    }

    @Override
    public UserInfo create(UserInfoDto.PostRequest createDto) {
        return null;
    }

    @Override
    public UserInfo update(String id, UserInfoDto.PostRequest updateDto) {
        return null;
    }

}
