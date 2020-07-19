package com.usargis.usargisapi.service.contract;

public interface ModelMapperService {
    <T> T map(Object source, Class<T> destinationType);

    void merge(Object source, Object destination);
}
