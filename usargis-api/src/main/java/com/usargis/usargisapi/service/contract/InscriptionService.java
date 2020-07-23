package com.usargis.usargisapi.service.contract;

import com.usargis.usargisapi.core.dto.InscriptionDto;
import com.usargis.usargisapi.core.model.Inscription;
import com.usargis.usargisapi.service.contract.common.CRUDService;

public interface InscriptionService extends CRUDService<Inscription, Long> {
    Inscription create(InscriptionDto.PostRequest createDto);

    Inscription update(Long id, InscriptionDto.PostRequest updateDto);
}
