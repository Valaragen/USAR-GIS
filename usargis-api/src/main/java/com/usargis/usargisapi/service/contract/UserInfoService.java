package com.usargis.usargisapi.service.contract;

import com.usargis.usargisapi.core.dto.UserInfoDto;
import com.usargis.usargisapi.core.model.UserInfo;
import com.usargis.usargisapi.service.contract.common.CRUDService;

import java.util.Optional;

public interface UserInfoService extends CRUDService<UserInfo, String> {
    Optional<UserInfo> findByUsername(String username);

    UserInfo create(UserInfoDto.PostRequest createDto);

    UserInfo update(String id, UserInfoDto.PostRequest updateDto);
}
