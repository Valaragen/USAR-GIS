package com.usargis.usargisapi.service.contract;

public interface SecurityService {
    boolean isSameUsernameThanAuthenticatedUser(String username);
    String getUsernameFromToken();
}
