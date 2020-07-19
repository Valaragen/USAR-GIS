package com.usargis.usargisapi.service.contract.common;

import java.util.List;
import java.util.Optional;

public interface CRUDService<ENTITY, ID> {
    List<ENTITY> findAll();

    Optional<ENTITY> findById(ID id);

    ENTITY save(ENTITY object);

    void delete(ENTITY object);
}
