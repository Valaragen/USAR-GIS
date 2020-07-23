package com.usargis.usargisapi.service.contract;

import com.usargis.usargisapi.core.dto.InscriptionDto;
import com.usargis.usargisapi.core.model.Inscription;
import com.usargis.usargisapi.service.contract.common.CRUDService;

public interface InscriptionService extends CRUDService<Inscription, Long> {
    Inscription create(InscriptionDto.InscriptionPostRequest createDto);

    Inscription update(Long id, InscriptionDto.InscriptionPostRequest updateDto);
}
