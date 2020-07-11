package com.usargis.usargisapi.model;

import com.usargis.usargisapi.model.common.ModelEntity;
import com.usargis.usargisapi.model.embeddable.InscriptionId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
public class Inscription extends ModelEntity {
    @EmbeddedId
    private InscriptionId id;

    @Column(nullable = false)
    private InscriptionStatus inscriptionStatus;
    @CreationTimestamp
    private LocalDateTime inscriptionDate;
}
