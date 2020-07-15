package com.usargis.usargisapi.service.contract;

import com.usargis.usargisapi.model.UserInfo;
import com.usargis.usargisapi.service.contract.common.CRUDService;

import java.util.Optional;

public interface UserInfoService extends CRUDService<UserInfo, String> {
    Optional<UserInfo> findByUsername(String username);
    boolean hasAccess(String username);
}
