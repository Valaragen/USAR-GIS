package com.usargis.usargisapi.model.converter;

import com.usargis.usargisapi.model.EventStatus;
import com.usargis.usargisapi.model.MissionStatus;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.stream.Stream;

@Converter(autoApply = true)
public class EventStatusConverter implements AttributeConverter<EventStatus, String> {

    @Override
    public String convertToDatabaseColumn(EventStatus status) {
        if (status == null) {
            return null;
        }
        return status.getCode();
    }

    @Override
    public EventStatus convertToEntityAttribute(String code) {
        if (code == null) {
            return null;
        }

        return Stream.of(EventStatus.values())
                .filter(c -> c.getCode().equals(code))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
