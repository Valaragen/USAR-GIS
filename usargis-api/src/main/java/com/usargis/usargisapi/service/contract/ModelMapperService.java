package com.usargis.usargisapi.service.contract;

public interface ModelMapperService {
    <T> T map(Object source, Class<T> destinationType);

    void map(Object source, Object destination);
}
