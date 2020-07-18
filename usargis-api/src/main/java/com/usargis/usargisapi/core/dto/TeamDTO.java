package com.usargis.usargisapi.core.dto;

import com.usargis.usargisapi.core.model.Mission;
import lombok.Data;

@Data
public class TeamDTO {
    private String name;
    private Mission mission;
}
