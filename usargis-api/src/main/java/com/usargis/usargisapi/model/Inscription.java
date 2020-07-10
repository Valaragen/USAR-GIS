package com.usargis.usargisapi.model;

import com.usargis.usargisapi.model.common.ModelEntity;
import com.usargis.usargisapi.model.embeddable.InscriptionId;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
public class Inscription extends ModelEntity {
    @EmbeddedId
    private InscriptionId id;

    private InscriptionStatus inscriptionStatus;
    private LocalDateTime inscriptionDate;
}
