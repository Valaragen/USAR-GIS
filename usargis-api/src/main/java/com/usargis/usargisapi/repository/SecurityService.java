package com.usargis.usargisapi.repository;

public interface SecurityService {
    boolean isSameUsernameThanAuthenticatedUser(String username);
}
