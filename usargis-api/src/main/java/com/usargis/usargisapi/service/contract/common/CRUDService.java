package com.usargis.usargisapi.service.contract.common;

import java.util.List;
import java.util.Optional;

public interface CRUDService<U, T> {
    List<U> findAll();
    Optional<U> findById(T id);
    U save(U object);
    void delete(U object);
}
