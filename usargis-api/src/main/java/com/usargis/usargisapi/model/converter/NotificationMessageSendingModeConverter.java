package com.usargis.usargisapi.model.converter;

import com.usargis.usargisapi.model.NotificationMessageSendingMode;
import com.usargis.usargisapi.model.NotificationStatus;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.stream.Stream;

@Converter(autoApply = true)
public class NotificationMessageSendingModeConverter implements AttributeConverter<NotificationMessageSendingMode, String> {

    @Override
    public String convertToDatabaseColumn(NotificationMessageSendingMode status) {
        if (status == null) {
            return null;
        }
        return status.getCode();
    }

    @Override
    public NotificationMessageSendingMode convertToEntityAttribute(String code) {
        if (code == null) {
            return null;
        }

        return Stream.of(NotificationMessageSendingMode.values())
                .filter(c -> c.getCode().equals(code))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
