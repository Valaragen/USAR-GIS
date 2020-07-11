package com.usargis.usargisapi.service.contract.common;

import java.util.Optional;

public interface CRUDService<U, T> {
    Optional<U> findById(T id);
    U save(U object);
    void delete(U object);
}
