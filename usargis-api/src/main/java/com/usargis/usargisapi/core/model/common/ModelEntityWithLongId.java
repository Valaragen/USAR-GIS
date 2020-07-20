package com.usargis.usargisapi.core.model.common;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Data
@MappedSuperclass
public abstract class ModelEntityWithLongId implements Serializable {
    @Version
    protected Long version;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;
}
