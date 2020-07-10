package com.usargis.usargisapi.model.converter;

import com.usargis.usargisapi.model.MissionStatus;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.stream.Stream;

@Converter(autoApply = true)
public class MissionStatusConverter implements AttributeConverter<MissionStatus, String> {

    @Override
    public String convertToDatabaseColumn(MissionStatus status) {
        if (status == null) {
            return null;
        }
        return status.getCode();
    }

    @Override
    public MissionStatus convertToEntityAttribute(String code) {
        if (code == null) {
            return null;
        }

        return Stream.of(MissionStatus.values())
                .filter(c -> c.getCode().equals(code))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
