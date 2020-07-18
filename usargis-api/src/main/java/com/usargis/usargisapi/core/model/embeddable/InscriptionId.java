package com.usargis.usargisapi.core.model.embeddable;

import com.usargis.usargisapi.core.model.Event;
import com.usargis.usargisapi.core.model.UserInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class InscriptionId implements Serializable {
    @ManyToOne(optional = false)
    private UserInfo userInfo;
    @ManyToOne(optional = false)
    private Event event;
}
