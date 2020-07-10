package com.usargis.usargisapi.model.converter;

import com.usargis.usargisapi.model.NotificationMessageContentType;
import com.usargis.usargisapi.model.NotificationMessageSendingMode;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.stream.Stream;

@Converter(autoApply = true)
public class NotificationMessageContentTypeConverter implements AttributeConverter<NotificationMessageContentType, String> {

    @Override
    public String convertToDatabaseColumn(NotificationMessageContentType status) {
        if (status == null) {
            return null;
        }
        return status.getCode();
    }

    @Override
    public NotificationMessageContentType convertToEntityAttribute(String code) {
        if (code == null) {
            return null;
        }

        return Stream.of(NotificationMessageContentType.values())
                .filter(c -> c.getCode().equals(code))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
