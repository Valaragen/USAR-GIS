package com.usargis.usargisapi.service.contract;

import com.usargis.usargisapi.core.dto.InscriptionDto;
import com.usargis.usargisapi.core.model.Inscription;
import com.usargis.usargisapi.core.model.embeddable.InscriptionId;
import com.usargis.usargisapi.service.contract.common.CRUDService;

public interface InscriptionService extends CRUDService<Inscription, InscriptionId> {
    Inscription create(InscriptionDto.PostRequest createDto);

    Inscription update(Long id, InscriptionDto.PostRequest updateDto);
}
