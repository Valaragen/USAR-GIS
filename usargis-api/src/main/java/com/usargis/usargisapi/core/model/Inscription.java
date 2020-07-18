package com.usargis.usargisapi.core.model;

import com.usargis.usargisapi.core.model.common.ModelEntity;
import com.usargis.usargisapi.core.model.embeddable.InscriptionId;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
public class Inscription extends ModelEntity {
    @Valid
    @NotNull
    @EmbeddedId
    private InscriptionId id;

    @NotNull
    @Column(nullable = false)
    private InscriptionStatus inscriptionStatus;
    @CreationTimestamp
    private LocalDateTime inscriptionDate;
}