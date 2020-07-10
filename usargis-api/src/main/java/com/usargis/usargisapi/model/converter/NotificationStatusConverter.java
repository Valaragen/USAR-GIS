package com.usargis.usargisapi.model.converter;

import com.usargis.usargisapi.model.MissionStatus;
import com.usargis.usargisapi.model.NotificationStatus;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.stream.Stream;

@Converter(autoApply = true)
public class NotificationStatusConverter implements AttributeConverter<NotificationStatus, String> {

    @Override
    public String convertToDatabaseColumn(NotificationStatus status) {
        if (status == null) {
            return null;
        }
        return status.getCode();
    }

    @Override
    public NotificationStatus convertToEntityAttribute(String code) {
        if (code == null) {
            return null;
        }

        return Stream.of(NotificationStatus.values())
                .filter(c -> c.getCode().equals(code))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
