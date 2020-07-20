package com.usargis.usargisapi.core.model.common;

import lombok.Data;

import javax.persistence.MappedSuperclass;
import javax.persistence.Version;
import java.io.Serializable;

@Data
@MappedSuperclass
public abstract class ModelEntity implements Serializable {
    @Version
    protected Long version;
}
